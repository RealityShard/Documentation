/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.session;

import com.realityshard.container.NetworkAdapter;
import java.util.UUID;


/**
 * Represents a shardlet.Session implementation.
 * This class is used to identify a client.
 * 
 * @author _rusty
 */
public final class GameSession extends GenericSession
{

    private final NetworkAdapter adapter;
    
    
    /**
     * Constructor.
     * 
     * @param       adapter                 The adapter to the basic networking functions
     * @param       uuid                    The unique ID of the underlying network
     *                                      client connection
     * @param       IP                      The IP of the client connection
     * @param       port                    The port of the client connection
     * @param       protocol                The protocol name of protocol this session
     *                                      uses internally.
     */
    public GameSession(NetworkAdapter adapter, UUID uuid, String IP, int port, String protocol)
    {
        super(uuid, IP, port, protocol);
        this.adapter = adapter;
    }
    

    /**
     * Invalidates this session, leading to the client socket channel being
     * kicked from the network interface.
     */
    @Override
    public void invalidate() 
    {
        adapter.handleForceClientDisconnect(this);
    }   
}
