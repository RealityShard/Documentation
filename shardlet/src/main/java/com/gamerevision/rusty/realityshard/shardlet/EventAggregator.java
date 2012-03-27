/**
 * Distributed under the GNU GLP v.3
 * Google it if you want to have a copy of the license.
 */


package com.gamerevision.rusty.realityshard.shardlet;


/**
 * An EventAggregator is the EventAggregator-pattern class implemented by the container
 * that will manage the events coming from or triggering the shardlets.
 * 
 * TODO: Check if the generics work they way they should.
 * 
 * @author _rusty
 */
public interface EventAggregator
{
    
    /**
     * Register a new listener with the event->listeners associations.
     * 
     * @param       event                   The listener will be bound to this event. Whenever
     *                                      it is triggered, the listener's service method will be invoked
     *                                      with a concrete implementation of this event.
     * @param       listener                The listener that will be triggered by the event
     */
    public <E extends ShardletEvent> void addListener(E event, ShardletEventListener<E> listener);
    
    
    /**
     * Trigger an event, the EventAggregator will try to distribute it to all 
     * registered listeners
     */
    public <E extends ShardletEvent> void triggerEvent();
    
    
    /**
     * Trigger an event, the EventAggregator will try to distribute it to all 
     * registered listeners
     * 
     * @param       action                  The <code>ShardletAction</code> that will be attached to the
     *                                      event.
     */
    public <E extends ShardletEvent> void triggerEvent(ShardletAction action);
}
