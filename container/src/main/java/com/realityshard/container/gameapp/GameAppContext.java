/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.container.Pacemaker;
import com.realityshard.container.gameapp.builder.GameAppContextFluentBuilder;
import com.realityshard.shardlet.*;
import com.realityshard.shardlet.events.context.ContextIncomingActionEvent;
import java.io.IOException;
import java.util.*;
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
public class GameAppContext extends GenericShardletContext
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(GameAppContext.class);
    
    
    protected ContextManager manager;
    protected ClassLoader loader;

    protected Pacemaker pacemaker;
    
    protected List<Shardlet> shardlets;

    
    /**
     * Constructor.
     * @see GameAppFluentBuilder
     */
    protected GameAppContext()
    {
        // this is empty, because we use a builder to
        // construct a new game app context
    }
        
    
    /**
     * Used by the ContextManager: provide this Context with a
     * new action, coming from a network client.
     * 
     * @param action 
     */
    public void handleIncomingAction(ShardletAction action)
    {
//        try 
//        {
//            // try decrypt/parse packet (or whatever else is done by the filters
//            protocols.get(action.getProtocol()).doInFilter(action);
//            
//            // invoke the shardlets, trigger the event that they are waiting for ;D
//            aggregator.triggerEvent(new ContextIncomingActionEvent(action));
//        } 
//        catch (IOException | ShardletException ex) 
//        {
//            LOGGER.error("Failed to handle a client action (a packet was unkown)", ex);
//        }
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
//        try 
//        {
//            // try encrypt/parse packet (or whatever else is done by the filters)
//            protocols.get(action.getProtocol()).doOutFilter(action);
//            
//            // pass it to the context manager
//            manager.sendAction(action);
//        } 
//        catch (IOException | ShardletException | NullPointerException ex) 
//        {
//            LOGGER.error("Failed to handle a client action (a packet or protocol was unkown)", ex);
//        }
    }
    
}
