/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.network;

import java.io.IOException;


/**
 *
 * @author _rusty
 */
public interface NetworkManager extends NetworkPacketConnector
{
    
    /**
     * Add a new listener to the specified port and run it.
     * The network manager does not know what the protocol is, it
     * just uses it to sign the packages.
     * 
     * @param       protocolName            The name of the protocol to be able to parse packets
     *                                      from clients connected to this server
     * @param       port                    The port of the protocol
     * @throws      IOException             If we could not create the listener
     */
    public void addNetworkListener(String protocolName, int port)
        throws IOException;
}
