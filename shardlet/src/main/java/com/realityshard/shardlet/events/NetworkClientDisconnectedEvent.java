/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.events;

import com.realityshard.shardlet.Event;
import com.realityshard.shardlet.Session;


/**
 * Triggered when a client disconnects.
 * Carries the (now invalid) session of the old client.
 * 
 * @author _rusty
 */
public final class NetworkClientDisconnectedEvent implements Event
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
