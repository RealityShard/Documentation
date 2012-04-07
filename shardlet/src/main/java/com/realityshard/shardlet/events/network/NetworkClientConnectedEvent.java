/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.events.network;

import com.realityshard.shardlet.ActionEvent;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.events.GenericNetworkEvent;


/**
 * Triggered when a client connects,
 * used as a carrier for messages of new clients.
 * 
 * @author _rusty
 */
public final class NetworkClientConnectedEvent extends GenericNetworkEvent
    implements ActionEvent
{
    
    private final ShardletAction action;

    
    /**
     * Constructor.
     * 
     * @param       action                  The first message from the new client
     */
    public NetworkClientConnectedEvent(ShardletAction action)
    {
        this.action = action;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The action that carries the first message from the client 
     */
    @Override
    public ShardletAction getAction() 
    {
        return action;
    }
    
}
