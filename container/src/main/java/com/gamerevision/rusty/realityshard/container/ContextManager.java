/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.container.events.InternalEventListener;
import com.gamerevision.rusty.realityshard.container.events.InternalStartupEvent;
import com.gamerevision.rusty.realityshard.schemas.ServerConfig;
import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.EventHandler;
import java.util.concurrent.Executor;


/**
 * This handles the containers shardlet-contexts in general.
 * One context is basically a representation of a game-app.
 * 
 * @author _rusty
 */
public final class ContextManager extends InternalEventListener
{
    
    private final EventAggregator internalAggregator;
    private ServerConfig serverConfig = null;
    
    
    /**
     * Constructor.
     * 
     * @param       internalAggregator
     * @param       executor
     * @param       gameAppsPath  
     */
    public ContextManager(EventAggregator internalAggregator, Executor executor, String gameAppsPath)
    {
        this.internalAggregator = internalAggregator;
        
        // TODO: validate the path and fail here if it doesnt exist
    }
    
    
    /**
     * EventHandler.
     * Load game app deployment descriptors and create those of the apps
     * that are explicitely marked as startup shards.
     * 
     * @param       event 
     */
    @EventHandler
    @Override
    public void handleStartupEvent(InternalStartupEvent event)
    {
        // save the server config for later usage
        serverConfig = event.getServerConfig();
        
        // TODO:
        // a) parse the game apps and save any found deployment descriptor
        //    for later use
        // b) create any found game apps that are marked as
        //    Start.WHEN_CONTAINER_STARTUP_FINISHED
    }
}
