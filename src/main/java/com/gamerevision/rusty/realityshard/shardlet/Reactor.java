/**
 * 
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
     * Add a new listener to the event-listener list
     * 
     * @param       event                   The event that will trigger the listener
     * @param       listener                The listener that will be triggered by the event
     */
    public <E extends ShardletEvent> void addListener(E event, ShardletEventListener listener);
    
    
    /**
     * Trigger an event, the reator will try to distribute it to all listeners
     * 
     * @param       event                   The event that will be triggered.
     */
    public <E extends ShardletEvent> void triggerEvent(E event);
}
