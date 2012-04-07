/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;


/**
 * Interface for any network layer objects
 * 
 * @author _rusty
 */
public interface NetworkLayer
{
    
    /**
     * Handle an outgoing packet.
     * 
     * This tries to send the raw data to the client specified by
     * the UUID (which should have been introduced by the network manager)
     * 
     * @param       rawData                 The raw packet data
     * @param       clientUID               A client UID
     * @throws      IOException             If the client was unreachable
     */
    public void handlePacket(ByteBuffer rawData, UUID clientUID)
        throws IOException;
    
    
    /**
     * Create a new client
     * 
     * @param       protocolName            The name of the protocol the client will use
     * @param       IP                      The IP of the new client
     * @param       port                    The port of the new client
     * @return      The UUID of the client if successful, or null if the client
     *              connection could not be created.
     * @throws      IOException             If the client could not be created
     */
    public UUID tryCreateClient(String protocolName, String IP, int port)
            throws IOException;
    
    
    /**
     * Forcefully disconnect a client
     * 
     * @param       clientUID               The UID of the client
     */
    public void disconnectClient(UUID clientUID);
    
    
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
    
    
    /**
     * Setter.
     * 
     * @param       connector               The connector that this network manager
     *                                      will output stuff to
     */
    public void setPacketHandler(NetworkConnector connector);
}
