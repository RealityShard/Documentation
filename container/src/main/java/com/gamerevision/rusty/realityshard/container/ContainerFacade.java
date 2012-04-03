/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.network.NetworkPacketEmitter;
import com.gamerevision.rusty.realityshard.network.NetworkPacketListener;
import com.gamerevision.rusty.realityshard.schemas.ServerConfig;
import com.gamerevision.rusty.realityshard.shardlet.ShardletAction;
import java.util.concurrent.ScheduledExecutorService;


/**
 * This class is used as the general Interface for working with the container.
 * Use this within your server-application to gain Shardlet-functionality.
 * 
 * @author _rusty
 */
public final class ContainerFacade
    implements NetworkPacketListener, NetworkPacketEmitter
{
    
    private final ScheduledExecutorService executor;
    private final ContextManager contextManager;
    private final SessionManager sessionManager;
    
    
    /**
     * Constructor.
     * 
     * @param       executor                The default thread scheduler, provided and
     *                                      created by the host application
     * @param       configPath              The path to the server config file
     * @param       schemaPath              The path to the schema file to validate the
     *                                      XML server config file
     * @throws      Exception               If there was any fatal error that keeps this
     *                                      container from being able to be executed
     */
    public ContainerFacade(ScheduledExecutorService executor, String configPath, String schemaPath) 
            throws Exception
    {
        // the executor is responsible for multithreading of this server
        // every internal event listener below will automatically be running parallel
        // depending on the executors decision, because when an event is triggered,
        // the event-aggregator will direct the event-handler invocations to the executor
        this.executor = executor;
        
        // try to load the server config
        // as this is usually where the most errors come from,
        // we can fail fast at an early stage of startup
        ServerConfig serverConfig = JaxbUtils.validateAndUnmarshal(ServerConfig.class, configPath, schemaPath);
                
        
        // create the service objects
        // if anything fails here, we have a severe problem and
        // cannot continue the execution of the container
        // that's why all exceptions will be delegated to the caller
        // of this constructor
        
        // - the context manager
        contextManager = new ContextManager(this, executor, serverConfig, schemaPath);
        
        // - the session manager
        sessionManager = new SessionManager();

        
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
    public void handlePacket(Byte[] rawData, int clientUID) 
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
    public void addPacketListener(NetworkPacketListener listener) 
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
    
}
