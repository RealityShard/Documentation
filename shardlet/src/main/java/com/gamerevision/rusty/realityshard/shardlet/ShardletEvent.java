/**
 * 
 */
package com.gamerevision.rusty.realityshard.shardlet;


/**
 * A general shardlet event that can be handled by a reactor.
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
     * 
     * @return      The <code>ShardletAction</code> of the event (a.k.a. the network message or payload)
     */
    public ShardletAction getAction();
}
