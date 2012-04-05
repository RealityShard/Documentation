/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container;

import com.realityshard.network.NetworkManager;
import com.realityshard.network.NetworkConnector;
import com.realityshard.schemas.ServerConfig;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletAction;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;


/**
 * This class is used as the general Interface for working with the container.
 * Use this within your server-application to gain Shardlet-functionality.
 * 
 * @author _rusty
 */
public final class ContainerFacade
    implements NetworkConnector
{
        
    /**
     * Hold client data.
     * Currently undocumented as its a simple and passive class.
     */
    private final class ClientWrapper
    {
        
        private final String protocolName;
        private final String IP;
        private final int port;
        private final Session session;
        private final UUID clientUID;
        
        
        public ClientWrapper(String protocolName, String IP, int port, Session session, UUID clientUID)
        {
            this.protocolName = protocolName;
            this.IP = IP;
            this.port = port;
            this.session = session;
            this.clientUID = clientUID;

        }

        public String getIP() { return IP; }

        public UUID getClientUID() { return clientUID; }

        public int getPort() { return port; }

        public String getProtocolName() { return protocolName; }

        public Session getSession() { return session; }
    }
    
    private final NetworkManager network;
    
    private final ScheduledExecutorService executor;
    private final ContextManager contextManager;
    private final SessionManager sessionManager;
    
    private final Map<UUID, ClientWrapper> clients;
    
    
    /**
     * Constructor.
     * 
     * @param       network                 The network manager of this application
     * @param       executor                The default thread scheduler, provided and
     *                                      created by the host application
     * @param       configPath              The path to the server config file
     * @param       schemaPath              The path to the schema file to validate the
     *                                      XML server config file
     * @param       gameAppsPath            The path to the game apps folder
     * @throws      Exception               If there was any fatal error that keeps this
     *                                      container from being able to be executed
     */
    public ContainerFacade(NetworkManager network, ScheduledExecutorService executor, File configPath, File schemaPath, File gameAppsPath) 
            throws Exception
    {
        // the network manager will handle the networking stuff,
        // like sending and recieving data.
        // TODO Check this:
        // We currently use a two-ways interface implemented by both this container
        // and the network manager, so we can plug them both together.
        // (They are both using almost equal methods of each other when it comes to signatures)
        this.network = network;
        this.network.addPacketListener(this);
        
        // the executor is responsible for multithreading of this server
        // every internal event listener below will automatically be running parallel
        // depending on the executors decision, because when an event is triggered,
        // the event-aggregator will direct the event-handler invocations to the executor
        this.executor = executor;
        
        // try to load the server config
        // as this is usually where the most errors come from,
        // we can fail fast at an early stage of startup
        File concreteConfig = new File(configPath, "server-config.xml");
        File concreteSchema = new File(schemaPath, "server-config.xsd");
        // strangely enough, we need a relative path here
        ServerConfig serverConfig = JaxbUtils.validateAndUnmarshal(ServerConfig.class, concreteConfig, concreteSchema);
                
        
        // create the service objects
        // if anything fails here, we have a severe problem and
        // cannot continue the execution of the container
        // that's why all exceptions will be delegated to the caller
        // of this constructor
        
        // - the context manager
        concreteSchema = new File(schemaPath, "game-app.xsd");
        contextManager = new ContextManager(this, executor, serverConfig, gameAppsPath, concreteSchema);
        
        // - the session manager
        sessionManager = new SessionManager();

        
        clients = new ConcurrentHashMap<>();
        // we'r done now. relax and wait for stuff coming from outside or
        // from the shardlets
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
        throws IOException
    {
        // we could do anything we want with this packet here,
        // but we don't
        
        // basically, what we will be doing though
        // is let the session manager attach a session to it
        // and then delegate it to the ContextManager
    }
    
    
    /**
     * Just the implementation of the interface.
     * 
     * @param       listener 
     */
    @Override
    public void addPacketListener(NetworkConnector listener) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * This is a gateway for network actions.
     * 
     * Note that this method is the only way to send packets via the network manager
     * 
     * @param       action 
     */
    public void handleOutgoingNetworkAction(ShardletAction action)
    {
        // send this packet straight to the NetworkInterface
    }

    
    /**
     * Called by the network manager when a client connects
     * 
     * @param       protocolName
     * @param       IP
     * @param       port
     * @param       clientUID
     * @throws      IOException 
     */
    @Override
    public void newClient(String protocolName, String IP, int port, UUID clientUID) 
            throws IOException 
    {
        // add the client here,
        clients.put(clientUID, new ClientWrapper(protocolName, IP, port, null, clientUID));
        
        // and TODO: inform the contexts
    }
    

    /**
     * Called by the network manager when a client disconnects
     * 
     * @param       clientUID 
     */
    @Override
    public void disconnectClient(UUID clientUID) 
    {
        // remove the client here,
        clients.remove(clientUID);
        
        // and TODO: inform the contexts
    }
    
}
