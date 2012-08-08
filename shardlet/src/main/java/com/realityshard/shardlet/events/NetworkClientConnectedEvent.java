/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.events;

import com.realityshard.shardlet.Action;


/**
 * Triggered when a client connects,
 * used as a carrier for messages of new clients.
 * 
 * @author _rusty
 */
public final class NetworkClientConnectedEvent implements ActionEvent
{
    
    private final Action action;

    
    /**
     * Constructor.
     * 
     * @param       action                  The first message from the new client
     */
    public NetworkClientConnectedEvent(Action action)
    {
        this.action = action;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The action that carries the first message from the client 
     */
    @Override
    public Action getAction() 
    {
        return action;
    }
    
}
