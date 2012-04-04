/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.schemas.GameApp;
import com.gamerevision.rusty.realityshard.schemas.ServerConfig;
import com.gamerevision.rusty.realityshard.schemas.Start;
import com.gamerevision.rusty.realityshard.shardlet.*;
import com.gamerevision.rusty.realityshard.shardlet.events.context.ContextIncomingActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


/**
 * This handles the containers shardlet-contexts in general.
 * One context is basically a representation of a game-app.
 * 
 * TODO: cleanup, check and fix the ugly code parts ;D
 * 
 * @author _rusty
 */
public final class ContextManager
{
   
    /**
     * Used for data storage only, and therefore left uncommented
     * TODO: possibly comment that
     */
    private final class GameAppInfo
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

        public File getPath() { return path; }
    }
    
    
    private final static Logger LOGGER = LoggerFactory.getLogger(GameAppContext.class);
    private final ContainerFacade facade;
    
    private final ScheduledExecutorService executor;
    private final File gameAppSchema;
    private final List<GameAppInfo> gameAppsInfo;
    
    private ServerConfig serverConfig = null;
    
    private List<GameAppContext> gameAppGeneral;
    private Map<Session, GameAppContext> gameAppBySession; 
    
    
    /**
     * Constructor.
     * 
     * @param       facade 
     * @param       executor
     * @param       config 
     * @param       gameAppsPath 
     * @param       gameAppSchema 
     * @throws      Exception  
     */
    public ContextManager(ContainerFacade facade, ScheduledExecutorService executor, ServerConfig config, File gameAppsPath, File gameAppSchema) 
            throws Exception
    {
        this.facade = facade;
        this.executor = executor;
        this.gameAppSchema = gameAppSchema;
        
        gameAppsInfo = new ArrayList<>();
        gameAppGeneral = new ArrayList<>();
        gameAppBySession = new ConcurrentHashMap<>();

        
        // save the server config for later usage
        serverConfig = config;
        
        // TODO:
        // a) parse the game apps and save any found deployment descriptor
        //    for later use
        // b) create any found game apps that are marked as
        //    Start.WHEN_CONTAINER_STARTUP_FINISHED
        
        // get all sub folders of the game app path
        // (these are the game app folders)        
        
        List<File> gameAppPaths = new ArrayList<>();
        

        File[] gameAppsSubDirs = gameAppsPath.listFiles();
        
        // failcheck
        if (gameAppsSubDirs == null) { throw new Exception("No game apps could be found in specified folder!"); }
        
        
        for (File path : gameAppsSubDirs) 
        {
            // and check them for sub dirs
            if (path.isDirectory())
            {
                //we've got a possible game-app dir here, lets search for the
                // GAME-INF folder
                for (File subPath : path.listFiles()) 
                {
                    if (subPath.isDirectory() && 
                            (subPath.getName().endsWith("GAME-INF") ||
                            subPath.getName().endsWith("GAME-INF/") ||
                            subPath.getName().endsWith("GAME-INF\\")))
                    {
                        // we've found the game app folder
                        // lets add it to our list of game app folders
                        gameAppPaths.add(subPath);
                    }
                }
            }
        }
        
        // lets check the paths for deployment descriptors next
        // they have the name "game.xml"
        for (File path : gameAppPaths) 
        {
            for (File subPath : path.listFiles()) 
            {
                if (subPath.isFile() && subPath.getName().endsWith("game.xml"))
                {
                    try 
                    {
                        // we've found a file that may fit our delpoyment descriptor schema
                        // lets try that
                        GameApp gameConfig = JaxbUtils.validateAndUnmarshal(GameApp.class, subPath, gameAppSchema);
                        
                        // lets add this game app to our list
                        gameAppsInfo.add(new GameAppInfo(gameConfig.getDisplayName(), path, gameConfig));
                    } 
                    catch (JAXBException | SAXException ex) 
                    {
                        LOGGER.error("GameApp " + subPath.getPath() + " has no valid delpoyment-descriptor", ex);
                    }
                }
            }
        }
        
        // ok, we've added all game app infos now
        // its time to start those game apps that contain the
        // startup option
        for (GameAppInfo gameAppInfo : gameAppsInfo) 
        {
            if (gameAppInfo.getConfig().getStartup() == Start.WHEN_CONTAINER_STARTUP_FINISHED)
            {
                try 
                {
                    // we need to create a new classloader for them first
                    ClassLoader cl = GameAppClassLoaderFactory.produceGameAppClassLoader(gameAppInfo.getPath());
                    
                    // now lets create the context
                    GameAppContext context = new GameAppContext(this, executor, cl, serverConfig, gameAppInfo.getConfig(), new HashMap<String, String>());
                    
                    // add the context to our general context list
                    // because we do not yet know it's sessions
                    gameAppGeneral.add(context);
                    
                    // thats all for now ;D
                } 
                catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException | ShardletException ex) 
                {
                    LOGGER.error("GameApp " + gameAppInfo.getName() + " could not be created.", ex);
                }
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
        // try to delegate this action to a game app
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
