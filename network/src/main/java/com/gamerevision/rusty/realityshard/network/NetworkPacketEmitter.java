/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.network;


/**
 * A class that can emit packets to its listeners
 * (e.g. incoming or outgoing)
 * 
 * @author _rusty
 */
public interface NetworkPacketEmitter 
{
    
    /**
     * Adds a listener to the list
     * If this class emitts a packet, the listener's handle method is called.
     * 
     * @param       listener 
     */
    public void addPacketListener(NetworkPacketListener listener);
}
