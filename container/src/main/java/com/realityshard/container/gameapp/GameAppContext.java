/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.container.utils.Pacemaker;
import com.realityshard.shardlet.*;
import com.realityshard.shardlet.events.network.NetworkClientDisconnectedEvent;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of a ShardletContext.
 * This is a basic implementation of an event-driven shardlet context,
 * that encapsulates a GameApp.
 * 
 * @see GameAppFluentBuilder
 * 
 * TODO: Review; Cleanup; Enhance the code in style and performance
 * 
 * @author _rusty
 */
public class GameAppContext extends GenericContext
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(GameAppContext.class);
    
    
    protected ContextManager manager;
    protected ClassLoader loader;

    protected Pacemaker pacemaker;
    
    protected List<Shardlet> shardlets;

    
    /**
     * Constructor.
     * 
     * @see GameAppFluentBuilder
     */
    protected GameAppContext()
    {
        // this is empty, because we use a builder to
        // construct a new game app context
        super();
    }
        
    
    /**
     * Decide whether a client is accepted or not.
     * 
     * @param       action                  The first message send by the new client
     * @return  
     */
    public boolean acceptClient(ShardletEventAction action)
    {
        ShardletActionVerifier acceptedVerifier = null;
        
        
        // ask our verifiers...
        // the non persistant first
        for (ShardletActionVerifier shardletActionVerifier : normalClientVerifiers) 
        {
            if (shardletActionVerifier.check(action))
            {
                // a verifier accepted a new client!
                // lets trigger the appropriate event
                action.triggerEvent(aggregator);
                
                // because the verfier is non persistant, we need to delete it from
                // the list after we left the loop ;D
                acceptedVerifier = shardletActionVerifier;
                
                // log it
                LOGGER.debug("Accepted client");
                
                break;
            }
        }
        
        
        // check if we found one:
        if (acceptedVerifier != null)
        {
            // we need to delete it from the list
            normalClientVerifiers.remove(acceptedVerifier);
            
            // everything else is already done, so lets end this method
            return true;
        }
        
        
        // now ask the persistant verifiers
        for (ShardletActionVerifier shardletActionVerifier : persistantClientVerifiers) 
        {
            if (shardletActionVerifier.check(action))
            {
                // a verifier accepted a new client!
                // lets trigger the appropriate event
                action.triggerEvent(aggregator);
                
                // log it
                LOGGER.debug("Accepted client");
                
                // we dont need to delete the verifier, so we can simply
                // end this method directly here
                return true;
            }
        }
        
        
        // log it
        LOGGER.debug("Didn't accept client");
        
        // if this is executed, all verifiers of our lists denied the client
        // so we can return false 
        return false;
    }
    
    
    /**
     * Used by the ContextManager: provide this Context with a
     * new action, coming from a network client.
     * 
     * The action will be published as a
     * <code>ContextIncomingActionEvent</code>
     * within the event aggregator of this game-app
     * 
     * @param action 
     */
    public void handleIncomingAction(ShardletEventAction action)
    {
        // send the event to the aggregator,
        // indirectly invoking the shardlets that handle new
        // incoming events
        action.triggerEvent(aggregator);
    }

    
    /**
     * Sends an action to a client specified by the Session object 
     * attached to the action.
     * If the client cannot be found, the action will not be transmitted and no error
     * is thrown.
     * This means: whenever you want to send stuff, make sure that everything is
     * alright with your messages.
     * 
     * @param       action 
     */
    @Override
    public void sendAction(ShardletAction action) 
    {
        // redirect the action to the context manager
        manager.sendAction(action);
    }
    
    
    /**
     * Inform the GameApp that we lost a client connection
     * 
     * Note that by the time this session reference arrives at the Shardlets of
     * the context, the facade will have deleted it from its list already, so you cannot
     * send any actions/packets to it anymore.
     * 
     * @param       session                 The session used to identify the client
     */
    public void handleLostClient(Session session)
    {
        aggregator.triggerEvent(new NetworkClientDisconnectedEvent(session));
    }
}
