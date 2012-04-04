/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;


/**
 * A class implementing this interface is able to handle raw network messages
 * be it incoming or outgoing raw messages.
 * 
 * It's reasonable to use this as an interface between the container and network
 * manager (with both implementing it)
 * 
 * TODO: This is maybe rather confusing when using it, so maybe we should rebuild this design?
 * TODO: The above and: THIS IS PATTERN ABUSAL, think of something else instead of such a lame interface
 * 
 * @author _rusty
 */
public interface NetworkPacketConnector 
{
    
    /**
     * Adds a listener to the list
     * If this class emitts a packet, the listener's handle method is called.
     * 
     * @param       listener 
     */
    public void addPacketListener(NetworkPacketConnector listener);
    
    
    /**
     * Handle an incoming or outgoing packet.
     * (depending on the context of the implementing class)
     * 
     * @param       rawData                 The raw packet data
     * @param       clientUID               A client UID (This should be introduced
     *                                      by the network manager)
     * @throws      IOException             If the client was unreachable, (not thrown by the container)
     */
    public void handlePacket(ByteBuffer rawData, UUID clientUID)
        throws IOException;
    
    
    /**
     * Handle a new client or create a new client (depending on the context)
     * 
     * @param       protocolName            The name of the protocol the client will use
     * @param       IP                      The IP of the new client
     * @param       port                    The port of the new client
     * @param       clientUID               The UID of the new client
     * @throws      IOException             If the client could not be created
     */
    public void newClient(String protocolName, String IP, int port, UUID clientUID)
            throws IOException;
    
    
    /**
     * A client disconnected or forcefully disconnect a client (depending on the context)
     * 
     * @param       clientUID               The UID of the client
     */
    public void disconnectClient(UUID clientUID);
}
