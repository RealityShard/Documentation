/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A concurrent implementation of a network layer object.
 * This handles incoming and outgoing data by usage of 
 * * ServerSocketChannels (as protocol-specific listeners)
 * * SocketChannels (as the client connections)
 * * Selectors (for managing the channels)
 * 
 * CAUTION!
 * The server sockets SelectionKey attachment is always its protocol name!
 * The socket channel SelectionKey attachment is always its UUID!
 * 
 * @author _rusty
 */
public final class ConcurrentNetworkManager
    implements NetworkLayer
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ConcurrentNetworkManager.class);
    private NetworkConnector packetHandler;
    
    private final Selector readSelector;
    private final Selector protSelector;
    
    private final ScheduledExecutorService executor;
    private final int bufferSize;
    
    private final Map<UUID, SelectionKey> channelKeys;
    
    
    /**
     * Constructor.
     * 
     * @param       executor                The executer used for this application
     *                                      (The network manager will automatically
     *                                      schedule new threads as needed)
     * @param       bufferSize              The buffer size for reading from the network.
     *                                      Should be enough to be able to parse at least
     *                                      one of the biggest packets 
     *                                      (Reducing packet fragmentation is a good thing)
     * @param       executionInterval       Interval in milliseconds when this managers main method
     *                                      will be scheduled.
     * @throws      IOException             If the selector could not be created
     */
    public ConcurrentNetworkManager(ScheduledExecutorService executor, int bufferSize, int executionInterval) 
            throws IOException
    {
        this.executor = executor;
        this.bufferSize = bufferSize;
        
        readSelector = Selector.open();
        protSelector = Selector.open();
        
        channelKeys = new ConcurrentHashMap<>();
        
        // after creating/doing all that stuff, 
        // lets create and run the main runnable object
        executor.scheduleAtFixedRate(getRunnable(), 0, executionInterval, TimeUnit.MICROSECONDS);
    }
    
    
    /**
     * Returns a thread that can be run to update this manager
     * 
     * @return      The object that holds runnable method (main loop in this case). 
     */
    public Runnable getRunnable()
    {
        // simply create an anonymous object that is runnable
        return new Runnable() 
        {
            /**
             * Will be called by our executor, so we dont need to care
             * about threads here. (Always a good choice, threads are nasty ;D)
             */
            @Override
            public void run() 
            {
                
                try 
                {
                    // lets check if we got new clients first:
                    
                    // let the selector do its work internally
                    // and save how many new clients we got
                    int count = protSelector.selectNow();
                    
                    // if we got more than 0 new connection, we
                    // will handle them now...
                    if (count != 0)
                    {
                        gotNewClients(protSelector.selectedKeys());
                    }
                    
                    // now lets check if we need to read any data:
                    
                    // let the selector do its work internally
                    // and save how many clients got data
                    count = readSelector.selectNow();
                    
                    // if we got more than 0 clients with new data,
                    // we should handle them
                    // Note: The method that handles the data will try to dipatch
                    // stuff to a new thread, because we've got quite a long 
                    // call stack that follows here (it ends at the GameApps
                    // event-aggregator)
                    if (count != 0)
                    {
                        gotNewData(readSelector.selectedKeys());
                    }
                    
                } 
                catch (IOException ex) 
                {
                    LOGGER.error("The protocol listener selector failed at selection.", ex);
                }
            }
        };
    }

    
    /**
     * Register any new client we got.
     * 
     * @param       protListeners           The listeners (as SelectionKeys) that
     *                                      have new client connections.
     * @throws      IOException             If anything went wrong with adding the clients.
     */
    private void gotNewClients(Set<SelectionKey> protListeners) 
            throws IOException
    {
        // we may have more than one new connection...
        for (Iterator<SelectionKey> it = protListeners.iterator(); it.hasNext();) 
        {
            // so we'll process every server socket we got from the selector
            SelectionKey key = it.next();
            it.remove();
            
            // failcheck here, to prevent false handling
            if (key.isValid() && key.isAcceptable())
            {
                // casting is necessary here...
                ServerSocketChannel chan = (ServerSocketChannel) key.channel();
                
                // now we can accept the new client
                SocketChannel newChan = chan.accept();
                
                // configure it so it can be registered with our selector
                newChan.configureBlocking(false);
                
                // finally, register it
                SelectionKey newKey = newChan.register(readSelector, SelectionKey.OP_READ);
                
                // now, all thats left to do is get some unique ID for that new connection
                // add it to our UUID->SelectionKey map, so we can write stuff to
                // it easily, later on
                // and finally notify the packetHandler (our container) that we've got a
                // new client:
                
                // get a new unique client id
                UUID uuid = UUID.randomUUID();
                
                // attach it to the client's key for later use
                newKey.attach(uuid);
                
                // and add the client to our map
                channelKeys.put(uuid, newKey);
                
                // log it
                LOGGER.debug("New SocketChannel: [UUID {}]", uuid);
                
                // tell the packet handler that we've got a new client
                String protocolName = (String) key.attachment();
                packetHandler.newClient(
                        protocolName, 
                        newChan.socket().getInetAddress().getHostAddress(), 
                        newChan.socket().getPort(), 
                        uuid);
                
                // thats it, we'r done
            }
        }
    }
    
    
    /**
     * Try handling any new data we got.
     * 
     * @param       protListeners           The channels (as SelectionKeys) that
     *                                      have new data from a client.
     * @throws      IOException             If anything went wrong with reading stuff.
     */
    private void gotNewData(Set<SelectionKey> dataKeys) 
            throws IOException
    {
        // we may have more than one channel with new data...
        for (Iterator<SelectionKey> it = dataKeys.iterator(); it.hasNext();) 
        {
            // so we'll process every socket we got from the selector
            SelectionKey key = it.next();
            it.remove();
            
            // failcheck here, to prevent false handling
            if (key.isValid() && key.isReadable())
            {
                // casting is necessary here...
                SocketChannel chan = (SocketChannel) key.channel();
                final UUID clientUID = (UUID) key.attachment();
                
                // set the buffer for reading stuff
                // use the bufferSize given at creating time of this object
                // Note: This should be at least big enough to handle the biggest
                // packet we can recieve (e.g. 512 bytes)
                final ByteBuffer readBuf = ByteBuffer.allocate(bufferSize);
                
                // we want this bytebuffer to interpret data in the little-endian byteorder
                readBuf.order(ByteOrder.LITTLE_ENDIAN);
                
                // read the data from the channel (that will also update our buffer)
                int count = chan.read(readBuf);
                
                // check if we got an EndOfStream
                // and simply kick the client if so
                if (count == -1)
                {
                    try 
                    {
                        // do the cleanup
                        key.cancel();
                        chan.close();
                        channelKeys.remove(clientUID);

                        // log it
                        LOGGER.debug("Disconnected SocketChannel: [UUID: {}]", clientUID);
                        
                        // also, inform the container
                        packetHandler.lostClient(clientUID);
                    } 
                    catch (IOException ex) 
                    {
                        LOGGER.error("Cloud not disconnect a SocketChannel.", ex);
                    }
                }
                else
                {
                    // Hint: If the buffer was not big enough to handle all the data
                    // the selector will issue another read later on, (TODO check if correct)
                    // but that is NOT recommended! packet fragmentation is considered evil
                    // because the protocol filters will have a hard time trying to 
                    // complete the data for the packet

                    // flip the buffer for reading lateron
                    readBuf.flip();

                    // log it
                    LOGGER.debug("C -> S [DATA {}]", getHexString(readBuf));

                    // finally call the handler
                    // CAUTION! WE WANT THIS TO BE IN A NEW THREAD!
                    // or at least let the pool manager decide if it's worth
                    // wasting a new thread for it ;D

                    executor.execute(new Runnable() 
                    {
                        /**
                        * Does nothing else than dispatching that packet data.
                        */
                        @Override
                        public void run() 
                        {
                            packetHandler.handlePacket(readBuf, clientUID);
                        }
                    });
                }
                
                // thats it, we'r done
            }
        }
    }

    
    /**
     * Handle an outgoing packet.
     * 
     * @param       rawData                 The raw packet data
     * @param       clientUID               A client UUID (This should be introduced
     *                                      by the network manager)
     */
    @Override
    public void handlePacket(ByteBuffer rawData, UUID clientUID) 
            throws IOException 
    {
        // make sure the buffer we send is set to position 0
        rawData.rewind();
        
        // get the right channel from our keys list
        SelectionKey key = channelKeys.get(clientUID);
        
        if (key != null)
        {
            SocketChannel chan = (SocketChannel) key.channel();

            // log it
            LOGGER.debug("S -> C [DATA {}]", getHexString(rawData));

            // write the data
            chan.write(rawData);
        }
    }
    
    
    /**
     * Try to create a new client connection
     * 
     * @param       protocolName            The name of the protocol the client will use
     * @param       IP                      The IP of the new client
     * @param       port                    The port of the new client
     * @throws      IOException             If the client could not be created
     */
    @Override
    public UUID tryCreateClient(String protocolName, String IP, int port)
            throws IOException
    {
        // open a new socket channel and try to establish a connection to
        // the given host
        SocketChannel chan = SocketChannel.open();
        
        // try to connect
        if (!chan.connect(new java.net.InetSocketAddress(IP, port))) 
        {
            throw new IOException(String.format("Could not connect to a new client: [IP: {} | PORT: {}]", IP, port));
        }
        
        // configure it so it can be registered with our selector
        chan.configureBlocking(false);

        // finally, register it
        SelectionKey newKey = chan.register(readSelector, SelectionKey.OP_READ);
        
        // now, all thats left to do is get some unique ID for that new connection
        // add it to our UUID->SelectionKey map, so we can write stuff to
        // it easily, later on

        UUID uuid = UUID.randomUUID();
        channelKeys.put(uuid, newKey);

        // log it
        LOGGER.debug("Created new SocketChannel: [UUID {}]", uuid);
        
        // return the identifier, so the connector can identify packets from this
        // client later on
        return uuid;
    }
    
    
    /**
     * Disconnect and delete a client.
     * 
     * @param       clientUID               The UID of the client
     */
    @Override
    public void disconnectClient(UUID clientUID)
    {
        // we'r going to forcefully disconnect the client
        // and delete its key from our keys map
        try 
        {
            SelectionKey key = channelKeys.get(clientUID);
            SocketChannel chan = (SocketChannel) key.channel();
            
            // do the cleanup
            key.cancel();
            chan.close();
            channelKeys.remove(clientUID);
            
            // log it
            LOGGER.debug("Disconnected SocketChannel: [UUID: {}]", clientUID);
        } 
        catch (IOException ex) 
        {
            LOGGER.error("Cloud not disconnect a SocketChannel.", ex);
        }
    }

    
    /**
     * Add a new listener to the specified port and run it.
     * The network manager does not know what the protocol is, it
     * just uses it to sign the packages.
     * 
     * @param       protocolName            The name of the protocol to be able to parse packets
     *                                      from clients connected to this server
     * @param       port                    The port of the protocol
     */
    @Override
    public void addNetworkListener(String protocolName, int port)
            throws IOException
    {
        // configure the listener
        ServerSocketChannel chan = ServerSocketChannel.open();
        chan.socket().bind(new java.net.InetSocketAddress(port));
        
        // set it non blocking, so we can add it to the selector
        chan.configureBlocking(false);
        
        // add it to our selector
        SelectionKey key = chan.register(protSelector, SelectionKey.OP_ACCEPT);
        
        // don't forget to save the protocol name
        key.attach(protocolName);
        
        // log it
        LOGGER.debug("New protocol listener as ServerSocketChannel: [NAME {} | PORT {}]", protocolName, port);
    }

    
    /**
     * Setter.
     * Used only once, when the container is registered with this manager.
     * 
     * @param       connector               The connector that this network manager
     *                                      will output stuff to
     */
    @Override
    public void setPacketHandler(NetworkConnector connector) 
    {
        packetHandler = connector;
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * For debug purpose only.
     * 
     * @param       bytes
     * @return 
     */
    private static String getHexString(ByteBuffer bytes)
    {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < bytes.limit(); i++) 
        {
            // this workaround abuses the larger 'integer' values,
            // to get unsigned byte-hexcode
            // note that there are no unsigned values in java
            String integerHex = Integer.toHexString(bytes.get());
            integerHex = integerHex.length() < 2 ? "0" + integerHex : integerHex;
            String byteHex = integerHex.substring(integerHex.length() - 2, integerHex.length());
            
            // we cann append this hex string representation of the byte to the debug string now
            result.append(byteHex);
        }
        
        // just in case ;D
        bytes.rewind();
        
        return result.toString();
    }
}
