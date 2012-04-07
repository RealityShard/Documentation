/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.network;

import java.nio.ByteBuffer;
import java.util.UUID;


/**
 * A class implementing this interface is able to handle raw network messages
 * and network client state updates.
 * 
 * @author _rusty
 */
public interface NetworkConnector 
{
    
    /**
     * Handle an incoming packet.
     * 
     * @param       rawData                 The raw packet data
     * @param       clientUID               A client UID
     */
    public void handlePacket(ByteBuffer rawData, UUID clientUID);
    
    
    /**
     * Handle a new client
     * 
     * @param       protocolName            The name of the protocol the client uses
     * @param       IP                      The IP of the new client
     * @param       port                    The port of the new client
     * @param       clientUID               The UID of the new client
     */
    public void newClient(String protocolName, String IP, int port, UUID clientUID);
    
    
    /**
     * A client disconnected
     * 
     * @param       clientUID               The UID of the client
     */
    public void lostClient(UUID clientUID);
}
