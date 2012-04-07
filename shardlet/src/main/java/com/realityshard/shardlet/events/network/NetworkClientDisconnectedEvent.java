/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.events.network;

import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.events.GenericNetworkEvent;


/**
 * Triggered when a client disconnects.
 * Carries the (now invalid) session of the old client.
 * 
 * @author _rusty
 */
public final class NetworkClientDisconnectedEvent extends GenericNetworkEvent
{
    private final Session session;
    
    
    /**
     * Constructor.
     * 
     * @param       session
     */
    public NetworkClientDisconnectedEvent(Session session)
    {
        this.session = session;
    }

    
    /**
     * Getter.
     * 
     * @return      The session attached to this event.
     */
    public Session getSession() 
    {
        return session;
    }
}
