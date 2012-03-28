/**
 * Distributed under the GNU GLP v.3
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.Event;
import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.EventListener;
import com.gamerevision.rusty.realityshard.shardlet.ShardletAction;
import java.util.ArrayList;
import java.util.List;
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
public final class ContextEventAggregator implements EventAggregator<ShardletAction>
{
    
    private Map<Class<? extends Event>, List<EventListener<? extends Event>>> eventMapping;
    
    
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
    @Override
    public <E extends Event> void addListener(Class<E> clazz, EventListener<E> listener)
    {
        // we want to add the listener to a list of listeners that all observer the same event
        
        if (eventMapping.containsKey(clazz))
        {
            // add the listener to the list if the entry already exists
            eventMapping.get(clazz).add(listener);
            return;
        }
        
        // or create a new entry if the event has no listeners yet
        ArrayList<EventListener<? extends Event>> list = new ArrayList<>();
        list.add(listener);
        
        eventMapping.put(clazz, list);
    }
    
    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate listeners
     */
    @Override
    public <E extends Event> void triggerEvent(Class<E> clazz)
    {
        for (EventListener<? extends Event> listener: eventMapping.get(clazz))
        {
            // for each listener in the listener collection,
            // try to service() the event
            try
            {
                // we need to instantiate the event
                // TODO: Can this be done any other way? We dont need the object here
                ((EventListener<E>) listener)
                    .service(clazz.newInstance());
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                // TODO Handle exceptions!
            }            
        }
    }

    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate listeners
     * 
     * @param       action                   The action that will be distributed.
     */
    @Override
    public <E extends Event> void triggerEvent(Class<E> clazz, ShardletAction action) 
    {
        for (EventListener<? extends Event> listener: eventMapping.get(clazz))
        {
            // for each listener in the listener collection,
            // try to service() the event
            try
            {
                // we need to instantiate the event
                // TODO: Can this be done any other way? We dont need the object here
                E event = clazz.newInstance();
                event.setAction(action);
                
                ((EventListener<E>) listener)
                    .service(event);
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                // TODO Handle exceptions!
            }            
        }
    }
}
