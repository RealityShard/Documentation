/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container;

import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.Action;
import java.io.IOException;


/**
 * Used to access the simple network-involving methods of the
 * ContainerFacade.
 * 
 * @author _rusty
 */
public interface NetworkAdapter 
{

    /**
     * Kick a specified session.
     * Can be called by the sessions themselfs (via Session.invalidate())
     *
     * @param       session
     */
    void handleForceClientDisconnect(Session session);

    
    /**
     * This is a gateway for network actions.
     *
     * Note that this method is the only way to send packets via the network manager
     *
     * @param       action
     */
    void handleOutgoingNetworkAction(Action action);

    
    /**
     * Tries to create a new protocol listener
     *
     * @param       name                    Name of the protocol (its just a string
     *                                      that will be used to identify clients
     *                                      later on)
     * @param       port                    The port that the protocol will be running on
     *                                      (meaning that the connection listener will use)
     * @throws      IOException             If the listener couldnt be created
     */
    void tryCreateProtocolListener(String name, int port) 
            throws IOException;

}
