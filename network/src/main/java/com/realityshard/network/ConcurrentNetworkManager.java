/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An implementation of the network manager interface used by the container
 * 
 * TODO: extends this to a more usable state
 * 
 * @author _rusty
 */
public final class ConcurrentNetworkManager 
    implements NetworkLayer, Runnable
{
      
    /**
     * Hold client data.
     * Currently undocumented as its a simple and passive class.
     */
    private final class ClientWrapper
    {
        
        private final String protocolName;
        private final SocketChannel channel;
        
        
        public ClientWrapper(String protocolName, SocketChannel channel)
        {
            this.protocolName = protocolName;
            this.channel = channel;
        }

        public SocketChannel getChannel() { return channel; }

        public String getProtocolName() { return protocolName; }
    }
    
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ConcurrentNetworkManager.class);
    
    private final Map<UUID, ClientWrapper> clients;
    private final Map<String, ServerSocketChannel> listeners;
    private NetworkConnector packetHandler;
    

    /**
     * Constructor.
     */
    public ConcurrentNetworkManager()
    {
        clients = new ConcurrentHashMap<>();
        listeners = new ConcurrentHashMap<>();
    }
    
    
    /**
     * Execute the main loop of this manager
     */
    @Override
    public void run() 
    {
        // test the listeners for new connections
        for (Map.Entry<String, ServerSocketChannel> entry : listeners.entrySet()) 
        {
            String prot = entry.getKey();
            ServerSocketChannel serverSocketChannel = entry.getValue();
            
            SocketChannel chan = null;
            try 
            {
                chan = serverSocketChannel.accept();
                // set the new channel to "not blocking"
                chan.configureBlocking(false);
            } 
            catch (IOException ex) 
            {
                LOGGER.warn("NetworkManager: Client connection initiation failed.");
            }
            
            if (chan != null)
            {
                // we've got a new client!
                // lets add him to our clients map
                UUID uuid = UUID.randomUUID();
                clients.put(uuid, new ClientWrapper(prot, chan));
                
                // log it
                LOGGER.debug("We've got a new client! [uuid: {}]", uuid);
                
                // also tell the packet handler that we've got a new client
                packetHandler.newClient(prot, chan.socket().getInetAddress().getHostAddress(), chan.socket().getPort(), uuid);
            }
        }
        
        // test connections for new packets
        for (Map.Entry<UUID, ClientWrapper> entry : clients.entrySet()) 
        {
            UUID uuid = entry.getKey();
            ClientWrapper clientWrapper = entry.getValue();
            
            // Bytes arrive in little endian order
            // and we always got a 2 bytes header sooo...
            ByteBuffer data = ByteBuffer.allocate(2);
            data.order(ByteOrder.LITTLE_ENDIAN);
            
            while (data.hasRemaining()) 
            {
                // Try to fill the ByteBuffer with data from the SocketChannel. This will read at least 1 byte of data
                int numberOfBytesRead = 0;
                try 
                {
                    numberOfBytesRead = clientWrapper.getChannel().read(data);
                } 
                catch (IOException ex) 
                {
                    LOGGER.warn("Error recieving data from a client. Possible loss of connection?");
                }

                // Check if an End-Of-Stream has been detected
                if (numberOfBytesRead == -1) 
                {
                    // inform the handler that we have lost the connection to
                    // a client
                    packetHandler.lostClient(uuid);
                    
                    LOGGER.debug("Lost connection reading data (End-Of-Stream)");
                }

                //LOGGER.debug("ByteBuffer has {} out of {} bytes filled. Bytes read this read(): {}", new Object[]{data.position(), data.limit(), numberOfBytesRead});
            }
            
            // log it
            LOGGER.debug("IN  packet [data: " + getHexString(data.array()) + "]");
            
            // call the handler
            packetHandler.handlePacket(data, uuid);
        }
    }
    
    
    /**
     * Handle an incoming or outgoing packet.
     * (depending on the context of the implementing class)
     * 
     * @param       rawData                 The raw packet data
     * @param       clientUID               A client UID (This should be introduced
     *                                      by the network manager)
     */
    @Override
    public void handlePacket(ByteBuffer rawData, UUID clientUID) 
            throws IOException 
    {
        clients.get(clientUID).getChannel().write(rawData);
        
        // log it
            LOGGER.debug("OUT packet [data: " + getHexString(rawData.array()) + "]");
    }
    
    
    /**
     * Create a new client
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
        SocketChannel chan = SocketChannel.open();
        
        // try to connect
        if (!chan.connect(new java.net.InetSocketAddress(IP, port))) 
        {
            throw new IOException("Could not connect to client.");
        }
        
        // get some random identifier
        UUID result = UUID.randomUUID();
        
        // add the new client to our list
        clients.put(result, new ClientWrapper(protocolName, chan));
        
        // log it
        LOGGER.debug("Created a new client connection [uuid: " + result + "]");
        
        // return the identifier, so the connector can identify packets from this
        // client later on
        return result;
    }
    
    
    /**
     * Disconnect and delete a client.
     * 
     * @param       clientUID               The UID of the client
     */
    @Override
    public void disconnectClient(UUID clientUID)
    {
        try 
        {
            clients.get(clientUID).getChannel().close();
            clients.remove(clientUID);
            
            // log it
            LOGGER.debug("Forcefully disconnected a client [uuid: " + clientUID + "]");
        } 
        catch (IOException ex) 
        {
            LOGGER.warn("Cloud not diconnect client.");
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
        
        // add it
        listeners.put(protocolName, chan);
        
        // log it
        LOGGER.debug("Added new network listener [name: " + protocolName + " | port: " + port + "]");
    }

    
    /**
     * Setter.
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
