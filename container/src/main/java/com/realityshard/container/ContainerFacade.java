/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container;

import com.realityshard.container.utils.GenericAction;
import com.realityshard.container.utils.JaxbUtils;
import com.realityshard.container.gameapp.ContextManager;
import com.realityshard.container.gameapp.builder.ContextManagerFluentBuilder;
import com.realityshard.container.session.GameSession;
import com.realityshard.network.NetworkConnector;
import com.realityshard.network.NetworkLayer;
import com.realityshard.schemas.serverconfig.ServerConfig;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletAction;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is used as the general Interface for working with the container.
 * Use this within your server-application to gain Shardlet-functionality.
 * 
 * Internal usage:
 * For all internal objects, this is a NetworkAdapter
 * 
 * External usage:
 * For the network manager object, this is a NetworkConnector
 * 
 * @author _rusty
 */
public final class ContainerFacade
    implements NetworkConnector, NetworkAdapter
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ContainerFacade.class);
    
    private final NetworkLayer network;
    
    private final ScheduledExecutorService executor;
    private final ContextManager contextManager;
    
    private final Map<UUID, GameSession> sessions;
    
    
    /**
     * Constructor.
     * 
     * @param       network                 The network manager of this application
     * @param       executor                The default thread scheduler, provided and
     *                                      created by the host application
     * @param       configPath              The path to the server config file
     * @param       schemaPath              The path to the schema file to validate the
     *                                      XML server config file
     * @param       protocolsPath           The path to the protocols folder
     * @param       gameAppsPath            The path to the game apps folder
     * @throws      Exception               If there was any fatal error that keeps this
     *                                      container from being able to be executed
     */
    public ContainerFacade(NetworkLayer network, ScheduledExecutorService executor, File configPath, File schemaPath, File protocolsPath, File gameAppsPath) 
            throws Exception
    {
        // the network manager will handle the networking stuff,
        // like sending and recieving data.
        // TODO Check this:
        // We currently use a two-ways interface implemented by both this container
        // and the network manager, so we can plug them both together.
        // (They are both using almost equal methods of each other when it comes to signatures)
        this.network = network;
        this.network.setPacketHandler(this);
        
        // the executor is responsible for multithreading of this server
        // every internal event listener below will automatically be running parallel
        // depending on the executors decision, because when an event is triggered,
        // the event-aggregator will direct the event-handler invocations to the executor
        this.executor = executor;
        
        // try to load the server config
        // as this is usually where the most errors come from,
        // we can fail fast at an early stage of startup
        File concreteServerConfig = new File(configPath, "server-config.xml");
        File concreteServerConfigSchema = new File(schemaPath, "server-config-schema.xsd");
        // strangely enough, we NEED a relative path here
        ServerConfig serverConfig = JaxbUtils.validateAndUnmarshal(ServerConfig.class, concreteServerConfig, concreteServerConfigSchema);
                
        
        // create the service objects
        // if anything fails here, we have a severe problem and
        // cannot continue the execution of the container
        // that's why all exceptions will be delegated to the caller
        // of this constructor
        
        // - the context manager
        File concreteProtocolSchema = new File(schemaPath, "protocol-schema.xsd");
        File concreteGameAppSchema = new File(schemaPath, "game-app-schema.xsd");
        contextManager = ContextManagerFluentBuilder
                .start()
                .useAdapter(this)          // can this be avoided?
                .useExecuter(executor)
                .useServerConfig(serverConfig)
                .useProtocolSchema(concreteProtocolSchema)
                .useGameAppSchema(concreteGameAppSchema)
                .setupProtocolsFromPath(protocolsPath)
                .setupGameAppsFromPath(gameAppsPath)
                .startupGameApps()
                .build();

        
        sessions = new ConcurrentHashMap<>();
        // we'r done now. relax and wait for stuff coming from outside or
        // from the shardlets
    }
    
    
    /**
     * Tries to create a new protocol listener
     * 
     * @param       name                    Name of the protocol (its just a string
     *                                      that will be used to identify clients
     *                                      later on)
     * @param       port                    The port that the protocol will be running on
     *                                      (meaning that the connection listener will use)
     * @throws      IOException             If the listener couldnt be created
     */ 
    @Override
    public void tryCreateProtocolListener(String name, int port) 
            throws IOException
    {
        network.addNetworkListener(name, port);
    }
    
    
    /**
     * This is a gateway for network actions.
     * 
     * Note that this method is the only way to send packets via the network manager
     * 
     * @param       action 
     */
    @Override
    public void handleOutgoingNetworkAction(ShardletAction action)
    {
        try
        {
            // send this packet straight to the NetworkInterface
            
            network.handlePacket(action.getBuffer(), action.getSession().getId());
        } 
        catch (IOException ex)
        {
            LOGGER.error("Could not handle outgoing message, maybe client disconnected already?", ex);
        }
    }
    
    
    /**
     * Kick a specified session.
     * Can be called by the sessions themselfs (via Session.invalidate())
     * 
     * @param session 
     */
    @Override
    public void handleForceClientDisconnect(Session session)
    {
        // disconnect the client network-wise
        network.disconnectClient(session.getId());
        
        // delete the session from our list
        sessions.remove(session.getId());
        
        // call the context manager to handle this now invalid session
        // NOTE: This is cyclic 
        // (e.g. a Shardlet called invalidate -> this method is called ->
        //  the context manager finds the context and informs it ->
        //  the context informs the Shardlets that a client has disconnected)
        contextManager.handleLostClient(session);
    }
    
    
    /**
     * Just the implementation of the interface.
     * This will be called by the network manager!
     * 
     * @param       rawData
     * @param       clientUID 
     */
    @Override
    public void handlePacket(ByteBuffer rawData, UUID clientUID)
    {
        // we could do anything we want with this packet here,
        // but we don't
        
        // basically, what we will be doing though
        // is create an action, attach a session to it (if we find the session, that is)
        // and then delegate it to the ContextManager
        Session session = sessions.get(clientUID);
        
        if (session != null)
        {
            contextManager.handleIncomingAction(new GenericAction(session));
        }
        // TODO: what if we've got no session?
    }

    
    /**
     * Called by the network manager when a client connects
     * 
     * @param       protocolName
     * @param       IP
     * @param       port
     * @param       clientUID
     */
    @Override
    public void newClient(String protocolName, String IP, int port, UUID clientUID) 
    {
        // add the client here,
        sessions.put(clientUID, new GameSession(this, clientUID, IP, port, protocolName));
        
        // and -T-O-D-O-: inform the context manager
        // currently, we dont need to inform it because it will automatically
        // recognize a message coming from an unkown client session
        // and then try to find a game-app-context that wants to 
        // accept the message and client
    }
    

    /**
     * Called by the network manager when a client disconnects
     * 
     * @param       clientUID 
     */
    @Override
    public void lostClient(UUID clientUID) 
    {
        // remove the client here,
        sessions.remove(clientUID);
        
        // and TODO: inform the contexts
    }
}
