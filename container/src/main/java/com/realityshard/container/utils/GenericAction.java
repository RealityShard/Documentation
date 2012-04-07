/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.utils;

import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletAction;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * A ShardletAction implementation used for
 * container internal messaging.
 * 
 * An object of this class may be converted to another action
 * later on, by the protocol handlers.
 * 
 * @author _rusty
 */
public class GenericAction implements ShardletAction
{

    private volatile Map<String, Object> attributes;
    private final String protocol;
    private final Session session;
    
    private volatile ByteBuffer buffer = null;
    
    
    /**
     * Constructor.
     * 
     * @param       session 
     */
    public GenericAction(Session session)
    {
        this.protocol = session.getProtocol();
        this.session = session;
        
        attributes = new HashMap<>();
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
    
    
    /**
     * Getter.
     * 
     * @return      The byte buffer that represents this objects
     *              underlying raw data.
     */
    @Override
    public ByteBuffer getBuffer() 
    {
        return buffer;
    }

    
    /**
     * Setter.
     * 
     * @param       buf 
     */
    @Override
    public void setBuffer(ByteBuffer buf) 
    {
        buffer = buf;
    }

    
    /**
     * Getter.
     * 
     * @return      The protocol of this action as string (the name)
     */
    @Override
    public String getProtocol() 
    {
        return protocol;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The session bound to this action.
     */
    @Override
    public Session getSession() 
    {
        return session;
    }
}
