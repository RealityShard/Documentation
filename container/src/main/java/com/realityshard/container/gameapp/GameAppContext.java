/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.container.utils.Pacemaker;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.Shardlet;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.events.context.ContextIncomingActionEvent;
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
     * @see GameAppFluentBuilder
     */
    protected GameAppContext()
    {
        // this is empty, because we use a builder to
        // construct a new game app context
        super();
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
    public void handleIncomingAction(ShardletAction action)
    {
        // send the event to the aggregator,
        // indirectly invoking the shardlets that handle new
        // incoming events
        aggregator.triggerEvent(new ContextIncomingActionEvent(action));
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
