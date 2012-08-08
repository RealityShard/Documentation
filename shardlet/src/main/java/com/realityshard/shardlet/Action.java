/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;

import java.nio.ByteBuffer;


/**
 * Defines an object to provide a message to or from a client, by:
 * 
 * A) The Shardlet container creates a <code>ShardletAction</code> object and passes
 * it as an argument to the Shardlet's handler method.
 *
 * B) The Shardlet creates a <code>ShardletAction</code> object and passes
 * it as an argument to the Shardlet container where it is distributed.
 * 
 * @author _rusty
 */
public interface Action
{
    
    public enum States 
    {
        /**
         * Meaning the action is only available as network-packet-bytes
         */
        SERIALIZED,
        
        /**
         * Meaning the action is available by accessing its attributes
         */
        DESERIALIZED
    }
    
    
    /**
     * Initialize the ShardletAction by serving the Session object that
     * this it will be distributed to, or that it is coming from.
     * 
     * @param       session                 The session object as a reference.
     */
    public void init(Session session);
    
    
    /**
     * Getter.
     * Use this when writing raw data into this action,
     * or reading raw data from this action
     * 
     * @return      The underlying NIO buffer.
     */
    public ByteBuffer getBuffer();
    
    
    /**
     * Setter.
     * Use this when writing raw data into this action
     * 
     * @param       buf                     The <code>ByteBuffer</code> object that will be
     *                                      set as the new Buffer
     */
    public void setBuffer(ByteBuffer buf);
    
    
    /**
     * Returns the name of the protocol the action uses.
     * The protocol is a string taken from the deployment descriptor of the protocol.
     *
     * @return      A <code>String</code> containing the protocol name,
     *              as used in the deployment descriptor.
     */    
    public String getProtocol();
    
    
    /**
     * Getter.
     * 
     * @return      The network session object.
     */
    public Session getSession();
    
    
    /**
     * Serialize this action, using a hardcoded serialization method
     */
    public boolean serialize();
    
    
    /**
     * Deserialize this action, using a hardcoded deserialization method
     */
    public boolean deserialize();
}
