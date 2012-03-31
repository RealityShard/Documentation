/**
 * Distributed under the GNU GPL v.3
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.container.events.InternalEventListener;
import com.gamerevision.rusty.realityshard.container.events.InternalStartupEvent;
import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.EventHandler;


/**
 * This handles the containers shardlet-contexts in general.
 * It's basically a representation of a game-app.
 * 
 * @author _rusty
 */
public class ContextManager extends InternalEventListener
{
    
    private final EventAggregator internalAggregator;
    
    
    /**
     * Constructor.
     * 
     * @param       internalAggregator 
     */
    public ContextManager(EventAggregator internalAggregator)
    {
        this.internalAggregator = internalAggregator;
    }
    
    
    /**
     * EventHandler.
     * 
     * @param       event 
     */
    @EventHandler
    @Override
    public void handleStartupEvent(InternalStartupEvent event)
    {
    }

}
