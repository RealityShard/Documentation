/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;

import java.nio.ByteBuffer;
import java.util.Enumeration;


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
public interface ShardletAction
{
    
    public enum States 
    {
        SERIALIZED,
        DESERIALIZED
    }
    
    
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
     * Returns the value of the named attribute as an <code>Object</code>,
     * or <code>null</code> if no attribute of the given name exists. 
     *
     * @param       name                    A <code>String</code> specifying the name 
     *                                      of the attribute
     * @return      The value-object or null.
     */
    public Object getAttribute(String name);
    
    
    /**
     * Stores an attribute in this action.
     * Attributes are reset between actions.
     *
     * @param       name                    A <code>String</code> specifying 
     *                                      the name of the attribute
     * @param       o                       The <code>Object</code> to be stored
     */
    public void setAttribute(String name, Object o);
    

    /**
     * Returns an <code>Enumeration</code> containing the
     * names of the attributes available to this action.
     *
     * @return      An <code>Enumeration</code> of attribute 
     *              names
     */
    public Enumeration<String> getAttributeNames();
    
    
    /**
     *
     * Removes an attribute from this action.  This method is not
     * generally needed as attributes only persist as long as the action
     * is being handled.
     *
     * @param       name                    A <code>String</code> specifying 
     *                                      the name of the attribute to remove
     */
    public void removeAttribute(String name);

}
