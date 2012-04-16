/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.session;

import com.realityshard.shardlet.Session;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;


/**
 * Encapsulates the most necessary methods of the shardlet.Session interface.
 * 
 * @author _rusty
 */
public abstract class GenericSession implements Session
{
    private final UUID uuid;
    private final String IP;
    private final int port;
    private final String protocol;
    
    private volatile EncryptionStates encState = EncryptionStates.UNENCRYPTED;
    private volatile Map<String, Object> attributes;
    
    
    /**
     * Constructor.
     * 
     * @param       uuid                    The unique ID of the underlying network
     *                                      client connection
     * @param       IP                      The IP of the client connection
     * @param       port                    The port of the client connection
     * @param       protocol                The protocol name of protocol this session
     *                                      uses internally.
     */ 
    public GenericSession(UUID uuid, String IP, int port, String protocol)
    {
        this.uuid = uuid;
        this.IP = IP;
        this.port = port;
        this.protocol = protocol;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The unique identifier as UUID
     */
    @Override
    public UUID getId() 
    {
        return uuid;
    }

    
    /**
     * Returns the Internet Protocol (IP) address of the client 
     * or last proxy that sent the request.
     *
     * @return      A <code>String</code> containing the 
     *              IP address of the client that sent the request
     */
    @Override
    public String getRemoteAddr() 
    {
        return IP;
    }

    
    /**
     * Returns the Internet Protocol (IP) source port of the client
     * or last proxy that sent the request.
     *
     * @return      An integer specifying the port number
     */ 
    @Override
    public int getRemotePort() 
    {
        return port;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The protocol-name as string
     */
    @Override
    public String getProtocol()
    {
        return protocol;
    }

    
    /**
     * Invalidates this session then unbinds any objects bound
     * to it. 
     */
    @Override
    public abstract void invalidate();
    
    
    /**
     * Set a new encryption state of this session.
     * 
     * @param       state                   The new encryption state 
     */
    @Override
    public void setEncryptionState(EncryptionStates state) 
    {
        encState = state;
    }
    
    
    /**
     * Get the current encryption state of this session.
     * 
     * @return      The current encryption state 
     */
    @Override
    public EncryptionStates getEncryptionState() 
    {
        return encState;
    }
    
    
    /**
     * Getter.
     * 
     * @param       name
     * @return      The value of the attribute if found
     */
    @Override
    public Object getAttribute(String name) 
    {
        return attributes.get(name);
    }

    
    /**
     * Getter.
     * 
     * @return      A list of all possible attribute names
     */
    @Override
    public Enumeration<String> getAttributeNames() 
    {
        return Collections.enumeration(attributes.keySet());
    }

    
    /**
     * Setter.
     * 
     * @param       name                    The name of the new attribute
     * @param       object                  The value of the attribute
     */
    @Override
    public void setAttribute(String name, Object object) 
    {
        attributes.put(name, object);
    }
    
    
    /**
     * Deletes an attribute from the attribute collection, if it exists
     * 
     * @param       name                    Name of the attribute that will be deleted.
     */
    @Override
    public void removeAttribute(String name) 
    {
        attributes.remove(name);
    }
}
