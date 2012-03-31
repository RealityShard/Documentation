/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.container.events.InternalEventListener;
import com.gamerevision.rusty.realityshard.container.events.InternalIncomingNetworkActionEvent;
import com.gamerevision.rusty.realityshard.container.events.InternalStartupEvent;
import com.gamerevision.rusty.realityshard.schemas.ServerConfig;
import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.ShardletAction;
import com.gamerevision.rusty.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.util.concurrent.Executor;


/**
 * This class is used as the general Interface for working with the container.
 * Use this within your server-application to gain Shardlet-functionality.
 * 
 * @author _rusty
 */
public final class ContainerFacade
{
    
    private final Executor executor;
    private final EventAggregator internalAggregator;
    private final InternalEventListener contextManager;
    private final InternalEventListener sessionManager;
    
    
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
    public ContainerFacade(Executor executor, String configPath, String schemaPath) 
            throws Exception
    {
        // the executor is responsible for multithreading of this server
        // every internal event listener below will automatically be running parallel
        // depending on the executors decision, because when an event is triggered,
        // the aggregator will direct the event-handler invocations to the executor
        this.executor = executor;
        
        
        // try to load the server config
        // as this is usually where the most errors come from,
        // we can fail fast at an early stage of startup
        ServerConfig serverConfig = JaxbUtils.validateAndUnmarshal(ServerConfig.class, configPath, schemaPath);
        
        
        // set up the internal aggregator
        // this is the main "blackboard" for publishing events
        internalAggregator = new ConcurrentEventAggregator(executor);
        
        
        // add the service objects to the aggregator
        // if anything fails here, we have a severe problem and
        // cannot continue the execution of the container
        // that's why all exceptions will be delegated to the caller
        // of this constructor
        
        // - the context manager
        contextManager = new ContextManager(internalAggregator, executor, serverConfig.getGameAppBasePath());
        internalAggregator.addListener(contextManager);
        
        // - the session manager
        sessionManager = new SessionManager(internalAggregator);
        internalAggregator.addListener(sessionManager);
        

        // start the event chain
        internalAggregator.triggerEvent(new InternalStartupEvent(serverConfig));
    }
    
    
    /**
     * This is a gateway for network actions.
     * 
     * Note that this method is here because usually only this class is used
     * by the host application, so this is the actual container interface
     * 
     * @param       action 
     */
    public void handleNetworkAction(ShardletAction action)
    {
        // we could do anything we want with this packet here,
        // but we don't
        internalAggregator.triggerEvent(new InternalIncomingNetworkActionEvent(action));
    }
}
