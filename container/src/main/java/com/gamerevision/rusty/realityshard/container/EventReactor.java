/**
 * 
 */
package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.Reactor;
import com.gamerevision.rusty.realityshard.shardlet.ShardletEvent;
import com.gamerevision.rusty.realityshard.shardlet.ShardletEventListener;


/**
 * Implementation of the event reactor pattern (or some kind of it)
 * 
 * This is a first draft only.
 * TODO Implement me!
 * 
 * @author _rusty
 *
 */
public class EventReactor implements Reactor
{
    
    /**
     * Constructor.
     *
     */
    public EventReactor()
    {
    }
    
    
    /**
     * Add a new listener to the event-listener list
     * 
     * @param       event                   The event that will trigger the listener
     * @param       listener                The listener that will be triggered by the event
     */
    @Override
    public <E extends ShardletEvent> void addListener(E event, ShardletEventListener listener)
    {
        // TODO Implement me!
    }
    
    
    /**
     * Trigger an event, the reator will try to distribute it to all listeners
     * 
     * @param       event                   The event that will be triggered.
     */
    @Override
    public <E extends ShardletEvent> void triggerEvent(E event)
    {
        // TODO Implement me!
    }
}
