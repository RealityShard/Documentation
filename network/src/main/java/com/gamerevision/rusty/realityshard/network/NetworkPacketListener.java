/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.network;


/**
 * A class implementing this interface is able to handle raw network messages
 * be it incoming or outgoing raw messages.
 * 
 * It's reasonable to use this as an interface between the container and network
 * manager (with both implementing it)
 * 
 * @author _rusty
 */
public interface NetworkPacketListener 
{
    
    /**
     * Handle an incoming or outgoing packet.
     * (depending on the context of the implementing class)
     * 
     * @param       rawData                 The raw packet data
     * @param       clientUID               A client UID (This should be introduced
     *                                      by the network manager)
     */
    public void handlePacket(Byte[] rawData, int clientUID);
}
