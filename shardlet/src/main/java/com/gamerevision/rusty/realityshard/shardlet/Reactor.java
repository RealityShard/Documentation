/**
 * Distributed under the GNU GLP v.3
 * Google it if you want to have a copy of the license.
 */


package com.gamerevision.rusty.realityshard.shardlet;


/**
 * A reactor is the event-reactor-pattern class implemented by the container
 * that will manage the events coming from or triggering the shardlets.
 * 
 * @author _rusty
 */
public interface Reactor
{
    
    /**
     * Register a new listener with the event->listeners associations.
     * 
     * @param       event                   The listener will be bound to this event. Whenever
     *                                      it is triggered, the listener's service method will be invoked
     *                                      with a concrete implementation of this event.
     * @param       listener                The listener that will be triggered by the event
     */
    public void addListener(ShardletEvent event, ShardletEventListener listener);
    
    
    /**
     * Trigger an event, the reactor will try to distribute it to all 
     * registered listeners
     * 
     * @param       event                   The event that the reactor will distribute among the registered
     *                                      listeners. (Just the ones that registered for this event only!)
     */
    public void triggerEvent(ShardletEvent event);
}
