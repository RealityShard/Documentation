/**
 * Distributed under the GNU GLP v.3
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.ShardletAction;
import com.gamerevision.rusty.realityshard.shardlet.ShardletEvent;
import com.gamerevision.rusty.realityshard.shardlet.ShardletEventListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Implementation of the event aggregator pattern (or some kind of it)
 * 
 * This is a first draft only.
 * TODO Implement me!
 * 
 * @author _rusty
 *
 */
public final class ContextEventAggregator implements EventAggregator
{
    
    private Map<Integer, ShardletEventListener<? extends ShardletEvent>> eventMapping;
    
    
    /**
     * Constructor.
     */
    public ContextEventAggregator()
    {
        eventMapping = new ConcurrentHashMap<>();
    }
    
    
    /**
     * Add a new listener to the event->listener association
     * 
     * @param       listener                The listener object. The aggregator will call the
     *                                      service method of this object in case of an event.
     */
    //@Override
    //public void addListener(ShardletEvent event, ShardletEventListener<? extends ShardletEvent> listener)
    //{
    //    eventMapping.put(event.getTypeHash(), listener);
    //}
    
    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate listeners
     */
    @Override
    public <E extends ShardletEvent> void triggerEvent() 
    {
        // TODO Implement me!
    }

    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate listeners
     * 
     * @param       action                   The action that will be distributed.
     */
    @Override
    public <E extends ShardletEvent> void triggerEvent(ShardletAction action) 
    {
        // TODO Implement me!
    }
}
