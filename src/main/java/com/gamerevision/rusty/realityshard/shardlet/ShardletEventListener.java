/**
 * 
 */
package com.gamerevision.rusty.realityshard.shardlet;


/**
 * All classes that implement this interface can be used by the
 * EventReactor, that will manage the ShardletEvents.
 * 
 * @author _rusty
 *
 */
public interface ShardletEventListener
{
    
    /**
     * This method will be called by the reactor.
     * 
     * @param       event                   The event that will be handled by the shardlet
     *                                      This can be more than one specific event.
     */
    public void service(ShardletEvent event);
}
