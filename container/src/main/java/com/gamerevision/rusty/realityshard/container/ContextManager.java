/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.container.events.InternalEventListener;
import com.gamerevision.rusty.realityshard.container.events.InternalStartupEvent;
import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.EventHandler;


/**
 * This handles the containers shardlet-contexts in general.
 * One context is basically a representation of a game-app.
 * 
 * @author _rusty
 */
public final class ContextManager extends InternalEventListener
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
