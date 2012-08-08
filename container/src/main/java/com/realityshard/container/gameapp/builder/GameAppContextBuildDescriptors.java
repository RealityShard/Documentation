/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.container.gameapp.ContextManager;
import com.realityshard.container.gameapp.GameAppContext;
import com.realityshard.container.utils.Pacemaker;
import com.realityshard.schemas.gameapp.AppInfo;
import com.realityshard.schemas.gameapp.InitParam;
import com.realityshard.schemas.gameapp.ShardletConfig;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.Shardlet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Provides the grammar for building steps used in the creation
 * of a GameAppContext
 * 
 * @author _rusty
 */
public interface GameAppContextBuildDescriptors 
{
    
    /**
     * Build step: add the context manager
     */
    public interface BuildManager
    {
        public BuildEventAggregator useManager(ContextManager manager);
    }

    
    /**
     * Build step: add the event aggregator
     */
    public interface BuildEventAggregator 
    {
        public BuildClassloader useAggregator(EventAggregator aggregator);
    }
    
    
    /**
     * Build step: add the setClassloader
     */
    public interface BuildClassloader 
    {
        public BuildInfo useClassloader(ClassLoader loader);
    }
    
    
    /**
     * Build step: add the info
     * 
     * extends BuildName, because you've got the choice here
     */
    public interface BuildInfo extends BuildName
    {
        public BuildShardlets useInfo(AppInfo info, ScheduledExecutorService executor);
    }
    
    
    /**
     * Build step: add the name
     */
    public interface BuildName 
    {
        public BuildDescription useName(String name);
    }
    
    
    /**
     * Build step: add the description of this game app
     */
    public interface BuildDescription 
    {
        /**
        * Build step: add the description of this game app
        * 
        * @param       description
        * @return      The next build step.
        */
        public BuildPacemaker useDescription(String description);
    }
    
    
    /**
     * Build step: build the pacemaker.
     */
    public interface BuildPacemaker 
    {
        public BuildInitParams useHeartBeat(int milliseconds);
        public BuildInitParams usePacemaker(Pacemaker pacemaker);
    }
    
    
    /**
     * Build step: save the init parameters.
     */
    public interface BuildInitParams 
    {
        public BuildShardlets useInitParams(List<InitParam> mandatoryParams);
        public BuildShardlets useInitParams(List<InitParam> mandatoryParams, Map<String, String> additionalParams);
    }
    
    
    /**
     * Build step: create or set the shardlets
     */
    public interface BuildShardlets 
    {
        public Build useShardlets(List<ShardletConfig> shardletConfigs) throws 
                ClassNotFoundException, 
                InstantiationException,
                IllegalAccessException,
                Exception;
        public Build useShardlets(Shardlet[] shardlets);
    }
    
    
    /**
     * Build step: finish building the game app
     */
    public interface Build 
    {
        public GameAppContext build();
    }
}
