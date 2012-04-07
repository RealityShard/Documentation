/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.container.gameapp.ContextManager;
import com.realityshard.container.gameapp.GameAppContext;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.Build;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildClassloader;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildDescription;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildEventAggregator;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildInfo;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildInitParams;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildManager;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildName;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildPacemaker;
import com.realityshard.container.gameapp.builder.GameAppContextBuildDescriptors.BuildShardlets;
import com.realityshard.container.utils.ConfigFactory;
import com.realityshard.container.utils.Pacemaker;
import com.realityshard.schemas.gameapp.AppInfo;
import com.realityshard.schemas.gameapp.InitParam;
import com.realityshard.schemas.gameapp.ShardletConfig;
import com.realityshard.shardlet.ConfigShardlet;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.Shardlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;


/**
 * This class is used to build a new Game App Context
 * 
 * @author _rusty
 */
public final class GameAppContextFluentBuilder extends GameAppContext implements
        BuildManager,
        BuildEventAggregator,
        BuildClassloader,
        BuildInfo,
        BuildName,
        BuildDescription,
        BuildPacemaker,
        BuildInitParams,
        BuildShardlets,
        Build
{

    /**
     * Constructor.
     */
    private GameAppContextFluentBuilder()
    {
        // private means
        // this will keep people from messing with this class.
        super();
    }
    
    
    /**
     * Initiate the building process
     * 
     * @return 
     */
    public static BuildManager start()
    {
        return new GameAppContextFluentBuilder();
    }
    
    
    /**
     * Step.
     * 
     * @param       manager
     * @return 
     */
    @Override
    public BuildEventAggregator useManager(ContextManager manager) 
    {
        this.manager = manager;
        return this;
    }
    
    
    /**
     * Step.
     * 
     * @param       aggregator
     * @return 
     */
    @Override
    public BuildClassloader useAggregator(EventAggregator aggregator) 
    {
        this.aggregator = aggregator;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       loader
     * @return 
     */
    @Override
    public BuildInfo useClassloader(ClassLoader loader) 
    {
        this.loader = loader;
        return this;
    }

    
    /**
     * Step. 
     * (Includes choices: useName, useDescription, useHeartBeat, usePacemaker, useInitParams)
     * 
     * @param       info
     * @param       executor 
     * @return 
     */
    @Override
    public BuildShardlets useInfo(AppInfo info, ScheduledExecutorService executor) 
    {
        // from what we got in the info we can build some of the
        // parts directly (but thats just an option)
        return this.useName(info.getDisplayName())
            .useDescription(info.getDescription())
            .useHeartBeat(executor, info.getHeartBeat().intValue())
            .useInitParams(info.getInitParam());
    }
    
    
    /**
     * Step.
     * 
     * @param       name
     * @return 
     */
    @Override
    public BuildDescription useName(String name) 
    {
        this.name = name;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       description
     * @return 
     */
    @Override
    public BuildPacemaker useDescription(String description) 
    {
        this.description = description;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       executor 
     * @param       milliseconds
     * @return 
     */
    @Override
    public BuildInitParams useHeartBeat(ScheduledExecutorService executor, int milliseconds) 
    {
        pacemaker = new Pacemaker(executor, aggregator, milliseconds);
        return this;
    }
    
    
    /**
     * Step. (Choice)
     * 
     * @param       pacemaker
     * @return 
     */
    @Override
    public BuildInitParams usePacemaker(Pacemaker pacemaker) 
    {
        this.pacemaker = pacemaker;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       mandatoryParams 
     * @return 
     */
    @Override
    public BuildShardlets useInitParams(List<InitParam> mandatoryParams) 
    {
        return useInitParams(mandatoryParams, new HashMap<String, String>());
    }
    
    
    /**
     * Step. (Choice)
     * 
     * @param       mandatoryParams 
     * @param       additionalParams
     * @return 
     */
    @Override
    public BuildShardlets useInitParams(List<InitParam> mandatoryParams, Map<String, String> additionalParams) 
    {
        // create a new map so we can mess with it
        initParams = new HashMap<>();
        
        // add the additional stuff first
        initParams.putAll(additionalParams);
        
        // then add the mandatory (meaning they come from the deployment descriptor)
        for (InitParam jaxbContxInitParam : mandatoryParams) 
        {
            initParams.put(jaxbContxInitParam.getName(), jaxbContxInitParam.getValue());
        }
        
        // we'r done, return the updated object
        return this;
    }

 
    /**
     * Step.
     * 
     * @param       shardletConfigs
     * @return
     * @throws      ClassNotFoundException
     * @throws      InstantiationException
     * @throws      IllegalAccessException
     * @throws      Exception 
     */
    @Override
    public Build useShardlets(List<ShardletConfig> shardletConfigs) throws 
            ClassNotFoundException, 
            InstantiationException,
            IllegalAccessException,
            Exception
    {
        // Now, the last thing that's missing is the Shardlets of course
        // we can do that like we did with the protocol filters
        // here's what we'll need:
        // - get the shardlet list
        // - loop trough and create a generic config for it
        // - get the shardlet class
        // - create the shardlet and call init(config)
        // - add it to the setAggregator and to our internal shardlet list
        shardlets = new ArrayList<>();
        
        for (ShardletConfig jaxbShardConf : shardletConfigs)
        {
            
            // to create a new shardlet, we need it's config
            // and it's class

            // create a new generic config out of the info
            // found in the deployment descriptor
            ConfigShardlet shardletConf = ConfigFactory.produceConfigShardlet(jaxbShardConf.getName(), this, jaxbShardConf.getInitParam());

            @SuppressWarnings("unchecked")
            Class<Shardlet> clazz = (Class<Shardlet>) loader.loadClass(jaxbShardConf.getClazz());

            // create the shardlet
            Shardlet newInst = clazz.newInstance();

            // init it
            newInst.init(shardletConf);

            // add it to our list
            shardlets.add(newInst);

            // add it to the aggregator
            aggregator.addListener(newInst);
        }
        
        // we'r done, return the updated object
        return this;
    }

    
    /**
     * Step. (Choice)
     * 
     * @param shardlets
     * @return 
     */
    @Override
    public Build useShardlets(Shardlet[] shardlets) 
    {
        for (Shardlet shardlet : shardlets) 
        {
            // add it to our list
            this.shardlets.add(shardlet);

            // add it to the aggregator
            aggregator.addListener(shardlet);
        }
        
        return this;
    }
    
    
    /**
     * Step. finish this build-process
     * 
     * @return 
     */
    @Override
    public GameAppContext build() 
    {
        // TODO: check if we got some cleanup to do here
        return this;
    }
}
