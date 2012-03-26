/**
 * 
 */
package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.ShardletEvent;
import com.gamerevision.rusty.realityshard.shardlet.ShardletEventListener;


/**
 * Implementation of the event aggregator pattern (or some kind of it)
 * 
 * This is a first draft only.
 * TODO Implement me!
 * 
 * @author _rusty
 *
 */
public class ContextEventAggregator implements EventAggregator
{
    
    /**
     * Constructor.
     *
     */
    public ContextEventAggregator()
    {
    }
    
    
    /**
     * Add a new listener to the event->listener association
     * 
     * @param       event                   The listener will be invoked when events of this
     *                                      type occur.
     * @param       listener                The listener object. The aggregator will call the
     *                                      service method of this object in case of an event.
     */
    @Override
    public void addListener(ShardletEvent event, ShardletEventListener listener)
    {
        // TODO Implement me!
    }
    
    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate listeners
     * 
     * @param       event                   The event that will be distributed.
     */
    @Override
    public void triggerEvent(ShardletEvent event)
    {
        // TODO Implement me!
    }
}
