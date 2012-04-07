/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.shardlet.*;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;


/**
 * Used for convenience: hides the ShardletContext interface
 * implementation
 * 
 * @author _rusty
 */
public abstract class GenericShardletContext implements ShardletContext
{
    
    protected EventAggregator aggregator;
    protected String name;
    protected String description;                  // TODO: not accessible from the shardlets... why?
    protected Map<String, String> initParams;
    protected Map<String, Object> attributes;

    
    /**
     * Sends an action to client specified by Session object attached to the action
     * if the client cannot be found, the action will not be transmitted and no error
     * is thrown.
     * This means: whenever you want to send stuff, make sure that everything is
     * alright with your messages.
     * 
     * @param       action 
     */
    @Override
    public abstract void sendAction(ShardletAction action);
    
    
    /**
     * Getter.
     * 
     * @return      The server name and version.
     */
    @Override
    public String getServerInfo() 
    {
        // TODO: do we need this?
        return "";//"Server: [" + serverConfig.getServerName() + "] Version: [" + serverConfig.getVersion() + "]";
    }
    
    
    /**
     * Getter.
     * 
     * @return      The name of this game app, as declared in the 
     *              deployment descriptor
     */
    @Override
    public String getShardletContextName() 
    {
        return name;
    }

    
    /**
     * Getter.
     * 
     * @return      The local aggregator (its scope is this context)
     */
    @Override
    public EventAggregator getAggregator() 
    {
        return aggregator;
    }

    
    /**
     * Getter.
     * Not supported yet.
     * 
     * @param       path                    The relative path to the ressource
     * @return      The ressource as input stream
     */
    @Override
    public InputStream getResourceAsStream(String path) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * Getter.
     * 
     * @param       name
     * @return      The value of the init parameter if found
     */
    @Override
    public String getInitParameter(String name) 
    {
        return initParams.get(name);
    }

    
    /**
     * Getter.
     * 
     * @return      A list of all possible parameter names 
     */
    @Override
    public Enumeration<String> getInitParameterNames() 
    {
        return Collections.enumeration(initParams.keySet());
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
