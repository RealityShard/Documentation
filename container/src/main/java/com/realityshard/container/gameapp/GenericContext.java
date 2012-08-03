/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.shardlet.*;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Used for convenience: hides the ShardletContext interface
 * implementation
 * 
 * @author _rusty
 */
public abstract class GenericContext implements ShardletContext
{
    
    protected EventAggregator aggregator;
    protected ScheduledExecutorService executor;
    protected String name;
    protected String description;                  // TODO: not accessible from the shardlets... why?
    protected Map<String, String> initParams;
    
    protected List<ShardletActionVerifier> normalClientVerifiers;
    protected List<ShardletActionVerifier> persistantClientVerifiers;
    
    
    /**
     * Constructor.
     */
    protected GenericContext()
    {
        normalClientVerifiers = new ArrayList<>();
        persistantClientVerifiers = new ArrayList<>();
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
     * 
     * @return      The global executor (a thread pool manager)
     */
    @Override
    public ScheduledExecutorService getExecutor()
    {
        return executor;
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
     * Tries to create a new game app, CURRENTLY ONLY ON THE SAME SERVER!
     * TODO: add ability to specify specific remote R:S server
     * 
     * @param       gameApp                 The 'display name' of the game app that we'll try to load
     * @param       parameters              Init-Params of the new game app (e.g. variable parameters that
     *                                      you don't want to set directly within the game.xml deployment-descriptor)
     * @return      The game app as a remote context reference
     * @throws      Exception               If something went wrong with creating the game app
     *                                      (Does not indicate that the game app doesnt exist,
     *                                      because in that case the returned reference would simply be null)
     */
    @Override
    public abstract ShardletContext tryCreateGameApp(String gameApp, Map<String, String> parameters)
            throws Exception;
    
    
    /**
     * Sends a new shardlet-event-action (meaning an action that
     * can be triggered as an event ;D)
     * 
     * Use this method to trigger events directly within the remote context,
     * by providing an action that creates these events.
     * 
     * Think of the 'event-action' as an event-wrapper.
     * 
     * @param       action                  The event-action that will be used in the remote
     *                                      context to trigger the desired concrete event
     */
    @Override
    public abstract void sendRemoteEventAction(ShardletEventAction action);
        
    
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
}
