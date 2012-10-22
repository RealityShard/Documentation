/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.container.gameapp.builder.GameAppContextFluentBuilder;
import com.realityshard.container.utils.ConfigFactory;
import com.realityshard.shardlet.ConfigShardlet;
import com.realityshard.shardlet.Shardlet;
import com.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


/**
 * This class holds all the data necessary to create a new game app.
 * In our case, this is done by using the classes that were added
 * to this factory as prototypes. This means they are not loaded dynamically.
 * 
 * @author _rusty
 */
public class GameAppFactoryDevEnv implements GameAppFactory
{
   
    private final static Logger LOGGER = LoggerFactory.getLogger(GameAppFactoryDevEnv.class);
    
    private final String name;
    private final int heartBeat;
    private final boolean isStartup;
    private final Map<String, String> initParams;
    private final Map<Shardlet, ConfigFactory.DataContainer> shardletPrototypes;
    
    
    /**
     * Constructor.
     * 
     * @param       name                    Name of this game app.
     * @param       heartBeat               HeartBeat interval in millisecs
     * @param       isStartup               Bool to inidicate whether this should
     *                                      be producing one game app when the container
     *                                      starts.
     * @param       initParams              The initParams for the game app
     */
    public GameAppFactoryDevEnv(
            String name, 
            int heartBeat, 
            boolean isStartup, 
            Map<String, String> initParams)
    {
        this.name = name;
        this.heartBeat = heartBeat;
        this.isStartup = isStartup;
        this.initParams = initParams;
        this.shardletPrototypes = new HashMap<>();
    }
    
    
    /**
     * Add a new shardlet prototype and the data used for initialization
     * 
     * @param       shardlet                The prototype shardlet
     * @param       name                    The name (as string) of the shardlet
     * @param       params                  The init-params of the shardlet
     * @return      This factory, so you can chain these kind of methods.
     */
    public GameAppFactoryDevEnv addShardlet(Shardlet shardlet, String name, Map<String, String> params)
    {
        shardletPrototypes.put(shardlet, new ConfigFactory.DataContainer(name, params));
        
        return this;
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
        return isStartup;
    }
    
    
    /**
     * Factory method.
     * 
     * @return      A newly created instance of the GameApp this factory produces.
     */
    @Override
    public GameAppContext produceGameApp(ContextManager manager, Map<String, String> additionalParams)
             throws Exception
    {
        // now, lets create the context
        // although it still is quite verbose
        GameAppContext context = GameAppContextFluentBuilder
                .start()
                .useManager(manager)
                .useAggregator(new ConcurrentEventAggregator())
                .useClassloader(ClassLoader.getSystemClassLoader())
                .useName(name)
                .useDescription("Development-Environment GameApp")
                .useHeartBeat(heartBeat)
                .useInitParams(initParams, additionalParams)
                .useShardlets(shardletPrototypes)
                .build();
        
        // finally return the context we just created
        return context;
    }
    
    
    /**
     * Clones the shardlet prototypes that this factory uses,
     * and initializes them during that process. They are ready to be used
     * with a new game app.
     * 
     * @return      The cloned shardlets.
     */
    private Map<Shardlet, ConfigFactory.DataContainer> cloneShardlets()
    {
        Map<Shardlet, ConfigFactory.DataContainer> result = new HashMap<>();
        
        for (Map.Entry<Shardlet, ConfigFactory.DataContainer> entry : shardletPrototypes.entrySet()) 
        {
            // just for convenience
            Shardlet shardlet = entry.getKey();
            ConfigFactory.DataContainer container = entry.getValue();
            
            // create the clone
            Shardlet clone = shardlet.clone();
            
            // and add it to the result set
            result.put(clone, container);
        }
        
        return result;
    }
}
