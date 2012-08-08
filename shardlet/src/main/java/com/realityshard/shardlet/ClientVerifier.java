/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;


/**
 * Objects implementing this interface can make a decision
 * on whether to accept a new client or not.
 * (Depending on its first message)
 * 
 * @author _rusty
 */
public interface ClientVerifier 
{
    
    /**
     * Check the first message of a new client.
     * (This action will be prased and decrypted if possible, so you can expect
     *  the most abstract format of it, but it is no special action, meaning it only
     *  has the standart methods defined by the ShardletAction interface)
     * 
     * @param       action                  The action resembling a packet from the 
     *                                      new client.
     * @return      True if we want to accept the client, and false if we don't
     */
    public boolean check(Action action);
}
