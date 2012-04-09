/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.host;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import org.junit.Test;


/**
 * Tests the socket channel
 * 
 * @author _rusty
 */
public class NetworkTest 
{
    /**
     * Test.
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    //@Test
    public void testSocketChannel() throws IOException, InterruptedException
    {
        SocketChannel chan = SocketChannel.open();
        chan.configureBlocking(true);
        
        // try to connect
        if (!chan.connect(new java.net.InetSocketAddress("127.0.0.1", 7112))) 
        {
            throw new IOException("Could not connect to client.");
        }
        
        for (int i = 0; i < 1000; i++) 
        {        
            // write stuff
            chan.write(ByteBuffer.wrap("lol".getBytes()));

            // and wait till we got the same stuff back
            ByteBuffer buf = ByteBuffer.allocate(3);

            chan.read(buf);

            // test if we still got more data
            chan.configureBlocking(false);
            assert (0 == chan.read(ByteBuffer.allocate(10000)));
            chan.configureBlocking(true);

            assert (Arrays.equals("lol".getBytes(), buf.array()));
        }

    }
    
    
    /**
     * For debug purpose only.
     * 
     * @param       bytes
     * @return 
     */
    private static String getHexString(byte[] bytes)
    {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < bytes.length; i++) 
        {
            result.append(Integer.toHexString(bytes[i]));
        }
        
        return result.toString();
    }
}
