/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * A ShardletAction implementation.
 * This class can be inherited if needed, but it can also be used as
 * a fully functional action.
 * 
 * @author _rusty
 */
public class GenericAction implements ShardletAction
{

    private String protocol;
    private Session session;
    
    private volatile ByteBuffer buffer = null;
    
    /**
     * Constructor.
     */
    public GenericAction()
    {
        protocol = null;
        session = null;
    }
    
    
    /**
     * Constructor.
     * 
     * @param       session 
     */
    public GenericAction(Session session)
    {
        this.protocol = session.getProtocol();
        this.session = session;
    }
    
    
    /**
     * Initialize the ShardletAction by serving the Session object that
     * this it will be distributed to, or that it is coming from.
     * 
     * @param       session                 The session object as a reference.
     */
    @Override
    public void init(Session session)
    {
        this.protocol = session.getProtocol();
        this.session = session;
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
