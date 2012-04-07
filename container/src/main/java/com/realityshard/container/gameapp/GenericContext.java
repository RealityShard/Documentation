/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.ShardletActionVerifier;
import com.realityshard.shardlet.ShardletContext;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Used for convenience: hides the ShardletContext interface
 * implementation
 * 
 * @author _rusty
 */
public abstract class GenericContext implements ShardletContext
{
    
    protected EventAggregator aggregator;
    protected String name;
    protected String description;                  // TODO: not accessible from the shardlets... why?
    protected Map<String, String> initParams;
    
    protected List<ShardletActionVerifier> normalClientVerifiers;
    protected List<ShardletActionVerifier> persistantClientVerifiers;
    private Map<String, Object> attributes;
    
    
    /**
     * Constructor.
     */
    protected GenericContext()
    {
        normalClientVerifiers = new ArrayList<>();
        persistantClientVerifiers = new ArrayList<>();
        attributes = new ConcurrentHashMap<>();
    }

    
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
     * Adds a new decider to the list. If we have a new client,
     * the context will run through the deciders checking if one of them
     * accepts the client.
     * 
     * @param       verifier                Checks whether we want to accept a new
     *                                      client based on its first message
     * @param       isPersistant            See above description. Should only be true
     *                                      if you want to auto-accept new clients.
     */
    @Override
    public void addClientVerifier(ShardletActionVerifier verifier, boolean isPersistant)
    {
        if (isPersistant)
        {
            persistantClientVerifiers.add(verifier);
        }
        else
        {
            normalClientVerifiers.add(verifier);
        }
    }
    
    
    /**
     * Clears the client verifiers list.
     * 
     * @param persistantVerifiersOnly       Determines whether the context should
     *                                      delete only persistant verifiers.
     */
    @Override
    public void clearClientVerifiers(boolean persistantVerifiersOnly)
    {
        // they will always be cleared:
        persistantClientVerifiers.clear();
        
        if (!persistantVerifiersOnly)
        {
            // clear them conditionally:
            normalClientVerifiers.clear();
        }
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
