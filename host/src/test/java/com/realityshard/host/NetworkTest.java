/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.host;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.junit.Test;


/**
 * Tests the socket channel
 * 
 * @author _rusty
 */
public class NetworkTest 
{
    @Test
    public void socketChannelTest() throws IOException, InterruptedException
    {
        SocketChannel chan = SocketChannel.open();
        
        // try to connect
        if (!chan.connect(new java.net.InetSocketAddress("127.0.0.1", 7112))) 
        {
            throw new IOException("Could not connect to client.");
        }
        
              
        chan.write(ByteBuffer.wrap("lol".getBytes()));
        
        Thread.sleep(1000);

    }
}
