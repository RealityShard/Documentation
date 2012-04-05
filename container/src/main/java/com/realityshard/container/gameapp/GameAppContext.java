/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.container.ContextManager;
import com.realityshard.container.Pacemaker;
import com.realityshard.container.ProtocolChain;
import com.realityshard.schemas.*;
import com.realityshard.shardlet.*;
import com.realityshard.shardlet.events.context.ContextIncomingActionEvent;
import com.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of a ShardletContext.
 * This is a basic implementation of an event-driven shardlet context,
 * that encapsulates a GameApp.
 * 
 * TODO: Review; Cleanup; Enhance the code in style and performance
 * 
 * @author _rusty
 */
public class GameAppContext implements ShardletContext
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(GameAppContext.class);
    
    protected ContextManager manager;
    protected EventAggregator aggregator;
    protected ClassLoader loader;
    protected String name;
    protected String description;
    
    protected Pacemaker pacemaker;
    protected Map<String, String> initParams;
    protected Map<String, Object> attributes;
    
    protected Map<String, ProtocolChain> protocols;
    protected List<Shardlet> shardlets;

    
    /**
     * Constructor.
     */
    protected GameAppContext()
    {
        
    }
    
    
    /**
     * Used by the ContextManager: provide this Context with a
     * new action, coming from a network client.
     * 
     * @param action 
     */
    public void handleIncomingAction(ShardletAction action)
    {
        try 
        {
            // try decrypt/parse packet (or whatever else is done by the filters
            protocols.get(action.getProtocol()).doInFilter(action);
            
            // invoke the shardlets, trigger the event that they are waiting for ;D
            aggregator.triggerEvent(new ContextIncomingActionEvent(action));
        } 
        catch (IOException | ShardletException ex) 
        {
            LOGGER.error("Failed to handle a client action (a packet was unkown)", ex);
        }
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
    public void sendAction(ShardletAction action) 
    {
        try 
        {
            // try encrypt/parse packet (or whatever else is done by the filters)
            protocols.get(action.getProtocol()).doOutFilter(action);
            
            // pass it to the context manager
            manager.sendAction(action);
        } 
        catch (IOException | ShardletException ex) 
        {
            LOGGER.error("Failed to handle a client action (a packet or protocol was unkown)", ex);
        }
    }
    
    
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
