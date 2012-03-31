/**
 * Distributed under the GNU GPL v.3
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.container.events.InternalEventListener;
import com.gamerevision.rusty.realityshard.container.events.InternalStartupEvent;
import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.EventHandler;


/**
 * This handles session objects.
 * When a new network action arrives, it will try to attach its session object
 * to it, or trigger an event to indicate that we have a new client.
 * 
 * @author _rusty
 */
public class SessionManager extends InternalEventListener
{
    
    private final EventAggregator internalAggregator;

    
    /**
     * Constructor.
     * 
     * @param       internalAggregator 
     */
    public SessionManager(EventAggregator internalAggregator)
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
