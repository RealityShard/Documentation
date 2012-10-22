/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.container.NetworkAdapter;
import com.realityshard.container.gameapp.builder.ContextManagerFluentBuilder;
import com.realityshard.container.gameapp.builder.GameAppContextFluentBuilder;
import com.realityshard.container.utils.ClassLoaderFactory;
import com.realityshard.container.utils.ProtocolChain;
import com.realityshard.schemas.gameapp.GameApp;
import com.realityshard.schemas.serverconfig.ServerConfig;
import com.realityshard.shardlet.Action;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import com.realityshard.shardlet.TriggerableAction;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This handles the containers shardlet-contexts in general.
 * One context is basically a representation of a game-app.
 * 
 * @see ContextManagerFluentBuilder
 * 
 * TODO: cleanup, check and fix the ugly code parts ;D
 * 
 * @author _rusty
 */
public class ContextManager
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ContextManager.class);
    
    protected NetworkAdapter adapter;
    
    protected File protocolSchema;
    protected File gameAppSchema;
    
    protected List<GameAppInfo> gameAppsInfo;
    
    protected ServerConfig serverConfig = null;
    
    protected Map<String, ProtocolChain> protocols;
    protected List<GameAppContext> gameAppGeneral;
    protected Map<Session, GameAppContext> gameAppBySession; 
    
    
    /**
     * Constructor.
     * 
     * @see ContextManagerFluentBuilder
     */
    protected ContextManager() 
    {
        gameAppsInfo = new ArrayList<>();
        gameAppGeneral = new ArrayList<>();
        gameAppBySession = new ConcurrentHashMap<>();
    }
    
    
    /**
     * Create a new GameAppContext, with its shardlets and stuff.
     * This does nothing if an app with the "name" doesnt exist.
     * 
     * @param       name                    The name of the game app that we'll try to load
     * @param       additionalParams        Any additional init parameters that will be globally available for the app
     * @param       parent                  A reference to the Game-App context that acted as a parent (if there is any)
     * @return      The reference to the newly created Game-App context
     * @throws      Exception               If aynthing went wrong with the creation of the game app
     */
    public GameAppContext createNewGameApp(String name, Map<String, String> additionalParams, ShardletContext parent)
            throws Exception
    {
        // lets try to find the app with the "name"
        for (GameAppInfo gameAppInfo : gameAppsInfo) 
        {
            // skip this app if it hasnt the right name
            if (!gameAppInfo.getName().equals(name))
            {
                continue;
            }
            
            try 
            {
                // we need to create a new setClassloader for it first
                // because the class loader depends on the path to the classes that it needs to load
                // so we create a new one with the path to our game app
                ClassLoader cl = ClassLoaderFactory.produceGameAppClassLoader(gameAppInfo.getPath(), ClassLoader.getSystemClassLoader());

                // now lets create the context
                // we need the config first...
                GameApp gaConf = gameAppInfo.getConfig();
                // ...so that we can keep this clean:
                // although it still is quite verbose
                GameAppContext context = GameAppContextFluentBuilder
                        .start()
                        .useManager(this)
                        .useAggregator(new ConcurrentEventAggregator())
                        .useClassloader(cl)
                        .useName(gaConf.getAppInfo().getDisplayName())
                        .useDescription(gaConf.getAppInfo().getDescription())
                        .useHeartBeat(gaConf.getAppInfo().getHeartBeat().intValue())
                        .useInitParams(gaConf.getAppInfo().getInitParam(), additionalParams)
                        .useShardlets(gaConf.getShardlet())
                        .build();

                // add the context to our general context list
                // because we do not yet know it's sessions
                gameAppGeneral.add(context);
                
                // let the game app know that it's been started and initialized
                // note that this could also be done by the fluent builder...
                // but as this is the place where we create the game app, we'll simply add it here
                context.handleIncomingAction(new GameAppCreatedEvent(parent));
                
                // log that we'r staring a game app
                LOGGER.debug("Starting a game app [name: " + context.getShardletContextName() + "]");
                
                // thats all for now ;D
                return context;
            } 
            catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) 
            {
                throw new Exception("Unable to create a new game app.", ex);
            }
        }
        
        // if nothing (no game app with that name) was found, return null
        return null;
    }
    
    
    /**
     * Used by the ContainerFacade: provides this ContextManager with a
     * new action, coming from a network client.
     * 
     * @param       action 
     */
    public void handleIncomingAction(Action action)
    {
        
        // try to decrypt and serialze the action before using it:
        
        // we want to pass it through the filter chain before useing it, 
        // and because incoming network streams may contain more than one action
        // we may have a list of action after using the protocol chain.
        // we may also have no action at all 
        // (if the packet send by the client was incomplete)
        List<TriggerableAction> filteredActions = new ArrayList<>();
        try 
        {
            // try decrypt/parse packet (or whatever else is done by the filters
            ProtocolChain prot = protocols.get(action.getProtocol());
            
            // failcheck
            if (prot != null)
            {
                // possible BUG TODO is this cast necessary?
                filteredActions = prot.doInFilter((TriggerableAction)action);
            }
            else
            {
                LOGGER.warn("Unkown protocol detected: {}", action.getProtocol());
            }
        
        } 
        catch (IOException ex) 
        {
            LOGGER.error("Failed to handle a client (incoming) action (a packet was unkown)", ex);
            return;
        }
        
        
        // now, because we may have a list of actions...
        // (remember: doInFilter may output more than one filter!)
        // we need to delegate each action to the game app it was made for:
        
        for (TriggerableAction filAction : filteredActions) 
        {
        
            // try to delegate this action to a game app
            // check if we got a game app for it:
            GameAppContext gameApp = gameAppBySession.get(action.getSession());

            if (gameApp != null)
            {
                // the session is already connected to one of our contexts, so lets
                // send it that new action so it can parse and distribute it
                gameApp.handleIncomingAction(filAction);

                // log that we'r staring a game app
                LOGGER.debug("Successfully delegated a new action");
            }
            else
            {
                // the packet obviously comes from a new client
                // so we need to send it to every game app out there,
                // to check if they want to handle it.

                // log that we'r staring a game app
                LOGGER.debug("We've got a new client!");

                for (GameAppContext gameAppContext : gameAppGeneral) 
                {
                    if (gameAppContext.acceptClient(filAction))
                    {
                        // we've found a game app that accepts the new client
                        // so we can create the association
                        gameAppBySession.put(action.getSession(), gameAppContext);

                        // and we can end the search here
                        break;
                    }
                }

                // TODO check if this is the appropriate behaviour!
                // if we didnt find any game app that want to accept this client
                // we do nothing
            }
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
    public void sendAction(Action action) 
    {
        // try to encrypt and serialze the action before using it:
        Action filteredAction = null;
        try 
        {
            // try encrypt/parse packet (or whatever else is done by the filters
            ProtocolChain prot = protocols.get(action.getProtocol());
            
            // failcheck
            if (prot != null)
            {
                filteredAction = prot.doOutFilter(action);
            }
            else
            {
                LOGGER.warn("Unkown protocol detected: {}", action.getProtocol());
            }
        } 
        catch (IOException ex) 
        {
            LOGGER.error("Failed to handle a shardlet (outgoing) action (a packet was unkown)", ex);
            return;
        }
        
        // try to send the action to the ContainerFacade
        if (filteredAction != null)
        {
            adapter.handleOutgoingNetworkAction(filteredAction);
        }
        else
        {
            LOGGER.warn("Action is null after filtering, may the protocol filters have failed?");
        }
    }
    
    
    /**
     * Inform the contexts that we lost a client connection
     * (This is called by the facade)
     * 
     * Note that by the time this session reference arrives at the Shardlets of
     * the context, the facade will have deleted it from its list already, so you cannot
     * send any actions/packets to it anymore.
     * 
     * @param       session                 The session used to identify the client
     */
    public void handleLostClient(Session session)
    {
        // try to get the context that handles this client
        GameAppContext context = gameAppBySession.get(session);
        
        if (context != null)
        {
            // and send it a message, so it knows that lost a client
            context.handleLostClient(session);
            
            // also remove the association
            gameAppBySession.remove(session);
        }
    }
}
