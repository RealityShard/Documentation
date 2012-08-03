/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Defines a set of methods that a shardlet uses to communicate with its
 * shardlet context.
 *
 * There is one context per game app  
 *
 * @author     _rusty
 */
public interface ShardletContext 
{
    
    /**
     * Used by shardlets to send an action (packet) to the client.
     * Note: The packet needs to be in its abstract state, meaning it
     * will be compiled and packed later on within the ProtocolFilterChain
     * 
     * @param       action 
     */
    public void sendAction(ShardletAction action);
    

    /**
     * Returns the name and version of the Shardlet container on which
     * the Shardlet is running.
     *
     * @return         A <code>String</code> containing at least the 
     *                Shardlet container name and version number
     */
    public String getServerInfo();
    
    
    /**
     * Returns the name of this web application corresponding to this
     * ShardletContext as specified in the deployment descriptor for this
     * web application by the display-name element.
     *
     * @return         The name of the web application or null if no name has been
     *                 declared in the deployment descriptor.
     */
    public String getShardletContextName();
    
    
    /**
     * Getter.
     * 
     * The event-aggregator is the place where every
     * shardlet event is distributed.
     * 
     * @return      The event aggregator bound to this context. 
     */
    public EventAggregator getAggregator();
    
    
    /**
     * Getter.
     * 
     * Beware of this method! This is not to be abused!
     * 
     * @return      The reference to the application-global executor.
     *              that's the actual thread pool manager.
     */
    public ScheduledExecutorService getExecutor();
    
    
    /**
     * Adds a new decider to the list. If we have a new client,
     * the context will run through the deciders checking if one of them
     * accepts the client.
     * 
     * If that decider accpeted the client, the context will check whether it is
     * persistant and delete it if not.
     * 
     * A hint on the isPersistant boolean:
     * It determines if the verifier will be deleted after it has
     * accepted the first client or not. 
     * If this is persistant,there will be no way of deleting it, 
     * except by calling the <code>clearClientVerifiers</code> 
     * method, that deletes every verifier of the list
     * 
     * @param       verifier                Checks whether we want to accept a new
     *                                      client based on its first message
     * @param       isPersistant            See above description. Should only be true
     *                                      if you want to auto-accept new clients.
     */
    public void addClientVerifier(ShardletActionVerifier verifier, boolean isPersistant);
    
    
    /**
     * Clears the client verifiers list.
     * 
     * If the paramter is true, this will ignore any verifier that is not
     * persistant and instead remove all persistant ones.
     * (This is especially helpful when you want to autoaccept clients
     *  only temporary, but dont want to loose any special non-persistant clients
     *  when you decide to end the auto accept period)
     * 
     * @param persistantVerifiersOnly       Determines whether the context should
     *                                      delete only persistant verifiers.
     */
    public void clearClientVerifiers(boolean persistantVerifiersOnly);
    
    
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
    public ShardletContext tryCreateGameApp(String gameApp, Map<String, String> parameters)
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
    public void sendRemoteEventAction(ShardletEventAction action);
    
    
    /**
     * Getter.
     * 
     * @param       name                    The name of the (generic) parameter.
     *                                      The object will try to find it based on that string.
     * @return      The parameter's value if found
     */
    public String getInitParameter(String name);


    /**
     * Getter.
     * 
     * @return      All parameter names (the keys without values)
     */
    public Enumeration<String> getInitParameterNames();
}


