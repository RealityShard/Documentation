/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.container.ClassLoaderFactory;
import com.realityshard.container.ContainerFacade;
import com.realityshard.container.gameapp.GameAppContext;
import com.realityshard.container.gameapp.builder.ContextManagerFluentBuilder;
import com.realityshard.container.gameapp.builder.GameAppContextFluentBuilder;
import com.realityshard.schemas.gameapp.GameApp;
import com.realityshard.schemas.gameapp.Start;
import com.realityshard.schemas.serverconfig.ServerConfig;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.ShardletException;
import com.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
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
   
    /**
     * Used for data storage only, and therefore left uncommented
     * This class stores data uased to create a game app.
     * It does not store an already created game app!
     */
    protected final class GameAppInfo
    {
        private final String name;
        private final File path;
        private final GameApp config;
        
        public GameAppInfo(String name, File path, GameApp config)
        {
            this.name = name;
            this.path = path;
            this.config = config;
        }
        
        public GameApp getConfig() { return config; }

        public String getName() { return name; }

        /**
         * Getter.
         * 
         * @return The path to the GAME-INF of this game app
         */
        public File getPath() { return path; }
    }
    
    
    private final static Logger LOGGER = LoggerFactory.getLogger(GameAppContext.class);
    
    protected ContainerFacade facade;
    
    protected ScheduledExecutorService executor;
    
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
     * @throws      Exception               If aynthing went wrong with the creation of the game app
     */
    public void createNewGameApp(String name, Map<String, String> additionalParams)
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
                        .useAggregator(new ConcurrentEventAggregator(executor))
                        .useClassloader(cl)
                        .useName(gaConf.getAppInfo().getDisplayName())
                        .useDescription(gaConf.getAppInfo().getDescription())
                        .useHeartBeat(executor, gaConf.getAppInfo().getHeartBeat().intValue())
                        .useInitParams(gaConf.getAppInfo().getInitParam(), additionalParams)
                        .useShardlets(gaConf.getShardlet())
                        .build();

                // add the context to our general context list
                // because we do not yet know it's sessions
                gameAppGeneral.add(context);

                // thats all for now ;D
            } 
            catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException | ShardletException ex) 
            {
                throw new Exception("Unable to create a new game app.", ex);
            }
        }
    }
    
    
    /**
     * Used by the ContainerFacade: provides this ContextManager with a
     * new action, coming from a network client.
     * 
     * @param       action 
     */
    public void handleIncomingAction(ShardletAction action)
    {
        
        // try to decrypt and serialze the action before using it:
        try 
        {
            // try decrypt/parse packet (or whatever else is done by the filters
            protocols.get(action.getProtocol()).doInFilter(action);
        } 
        catch (IOException | ShardletException ex) 
        {
            LOGGER.error("Failed to handle a client action (a packet was unkown)", ex);
            return;
        }
        
        // try to delegate this action to a game app
        // check if we got a game app for it:
        GameAppContext gameApp = gameAppBySession.get(action.getSession());
        
        if (gameApp != null)
        {
            // the session is already connected to one of our contexts, so lets
            // send it that new action so it can parse and distribute it
            gameApp.handleIncomingAction(action);
        }
        else
        {
            // the packet obviously comes from a new client
            // so we need to send it to every game app out there,
            // to check if they want to handle it.
            
            // TODO: implement me and add the methods to the contexts.
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
    public void sendAction(ShardletAction action) 
    {
        // try to send the action to the ContainerFacade
        facade.handleOutgoingNetworkAction(action);
    }
}
