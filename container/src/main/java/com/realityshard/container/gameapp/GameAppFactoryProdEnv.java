/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.container.gameapp.builder.GameAppContextFluentBuilder;
import com.realityshard.container.utils.ClassLoaderFactory;
import com.realityshard.schemas.gameapp.GameApp;
import com.realityshard.schemas.gameapp.Start;
import com.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;


/**
 * This class holds all the data necessary to create a new game app.
 * In our case, this is done by dynamically loading the game app parts,
 * as this class is meant to be used in production environment.
 * 
 * @author _rusty
 */
public final class GameAppFactoryProdEnv implements GameAppFactory 
{
    private final String name;
    private final File path;
    private final GameApp config;

    
    /**
     * Constructor.
     * 
     * @param name
     * @param path
     * @param config 
     */
    public GameAppFactoryProdEnv(String name, File path, GameApp config) 
    {
        this.name = name;
        this.path = path;
        this.config = config;
    }

    
    /**
     * Getter.
     * 
     * @return      The Game-App's name (may be used to identifiy this game app!)
     */
    @Override
    public String getName() 
    {
        return name;
    }
    
    
    /**
     * Should one of the game apps of this factory be loaded at the start of the
     * container?
     * 
     * @return 
     */
    @Override
    public boolean isStartup()
    {
        return (config.getAppInfo().getStartup() == Start.WHEN_CONTAINER_STARTUP_FINISHED);
    }
    
    
    /**
     * Factory method.
     * 
     * @return      A newly created instance of the GameApp this factory produces,
     *              or null if there were errors during production.
     */
    @Override
    public GameAppContext produceGameApp(ContextManager manager, Map<String, String> additionalParams)
             throws Exception
    {
        GameAppContext context = null;
        
        try 
        {
            // we need to create a new setClassloader for it first
            // because the class loader depends on the path to the classes that it needs to load
            // so we create a new one with the path to our game app
            ClassLoader cl = ClassLoaderFactory.produceGameAppClassLoader(path, ClassLoader.getSystemClassLoader());

            // now lets create the context
            // although it still is quite verbose
            context = GameAppContextFluentBuilder
                    .start()
                    .useManager(manager)
                    .useAggregator(new ConcurrentEventAggregator())
                    .useClassloader(cl)
                    .useName(config.getAppInfo().getDisplayName())
                    .useDescription(config.getAppInfo().getDescription())
                    .useHeartBeat(config.getAppInfo().getHeartBeat().intValue())
                    .useInitParams(config.getAppInfo().getInitParam(), additionalParams)
                    .useShardlets(config.getShardlet())
                    .build();
        } 
        catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) 
        {
            throw new Exception("Unable to create a new game app.", ex);
        }
        
        // finally return the context we just created
        return context;
    }

}
