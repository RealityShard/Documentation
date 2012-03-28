/**
 * Distributed under the GNU GLP v.3
 */

package com.gamerevision.rusty.realityshard.shardlet;


/**
 * A general shardlet event that can be handled by an event-aggregator.
 * An event may contain an action, which is basically a message from
 * or to a client.
 * 
 * @author _rusty
 *
 */
public interface ShardletEvent
{
    
    /**
     * Getter.
     * Used to destinguish different event types.
     * 
     * @return      A type-unique number.
     */
    public int getTypeHash();
    
    
    /**
     * Getter.
     * 
     * @return      The <code>ShardletAction</code> of the event (a.k.a. the network message or payload)
     */
    public ShardletAction getAction();
    
    /**
     * Setter.
     * 
     * @param       action                  The <code>ShardletAction</code> of the event 
     *                                      (a.k.a. the network message or payload)
     */
    public void setAction(ShardletAction action);
}
