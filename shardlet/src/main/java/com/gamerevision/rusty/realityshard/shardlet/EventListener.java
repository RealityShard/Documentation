/**
 * Distributed under the GNU GLP v.3
 */

package com.gamerevision.rusty.realityshard.shardlet;


/**
 * All classes that implement this interface can be used by the
 * EventAggregator, that will manage the ShardletEvents.
 * 
 * @author _rusty
 *
 */
public interface EventListener<E extends Event>
{
    
    /**
     * This method will be called by the aggregator.
     * 
     * @param       event                   The event that will be handled by the shardlet
     */
    public void service(E event);
}
