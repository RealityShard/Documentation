/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.container.gameapp.GameAppContext;
import com.realityshard.container.GenericConfig;
import com.realityshard.container.Pacemaker;
import com.realityshard.container.ProtocolChain;
import com.realityshard.schemas.InitParam;
import com.realityshard.schemas.Protocol;
import com.realityshard.schemas.ProtocolFilterConfig;
import com.realityshard.schemas.ShardletConfig;
import com.realityshard.shardlet.Config;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.ProtocolFilter;
import com.realityshard.shardlet.Shardlet;
import com.realityshard.shardlet.ShardletException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;


/**
 *
 * @author _rusty
 */
public final class GameAppFluentBuilder extends GameAppContext implements
        GameAppBuildEventAggregator,
        GameAppBuildClassloader,
        GameAppBuildName,
        GameAppBuildDescription,
        GameAppBuildPacemaker,
        GameAppBuildInitParams,
        GameAppBuildProtocols,
        GameAppBuildShardlets,
        GameAppBuildFinish
{

    /**
     * Constructor.
     */
    private GameAppFluentBuilder()
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
    public static GameAppBuildClassloader build()
    {
        return new GameAppFluentBuilder();
    }
    
    
    /**
     * Step.
     * 
     * @param       aggregator
     * @return 
     */
    @Override
    public GameAppBuildClassloader aggregator(EventAggregator aggregator) 
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
    public GameAppBuildName classloader(ClassLoader loader) 
    {
        this.loader = loader;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       name
     * @return 
     */
    @Override
    public GameAppBuildDescription name(String name) 
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
    public GameAppBuildPacemaker description(String description) 
    {
        this.description = description;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       milliseconds
     * @return 
     */
    @Override
    public GameAppBuildInitParams heartBeat(ScheduledExecutorService executor, int milliseconds) 
    {
        pacemaker = new Pacemaker(executor, aggregator, milliseconds);
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       mandatoryParams 
     * @param       additionalParams
     * @return 
     */
    @Override
    public GameAppBuildProtocols initParams(List<InitParam> mandatoryParams, Map<String, String> additionalParams) 
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
     * @param       protocols
     * @return
     * @throws      ClassNotFoundException
     * @throws      InstantiationException
     * @throws      IllegalAccessException
     * @throws      ShardletException  
     */
    @Override
    public GameAppBuildShardlets protocols(List<Protocol> protocols) throws 
            ClassNotFoundException, 
            InstantiationException,
            IllegalAccessException,
            ShardletException
    {
        this.protocols = new ConcurrentHashMap<>();
        
        for (Protocol jaxbProtConf : protocols)
        {
            // we've got a list of filters that do something with
            // incoming packets, and we've got a list of those that
            // handle outgoing packets
            List<ProtocolFilter> inFilters = new ArrayList<>();
            List<ProtocolFilter> outFilters = new ArrayList<>();
            
            // each protocol may consist of multiple protocol filters
            for (ProtocolFilterConfig jaxbProtFiltConf : jaxbProtConf.getProtocolFilter())
            {
                // to create a new protocol filter, we need it's config
                // and it's class
                
                // create a new generic config out of the info
                // found in the deployment descriptor
                Config filterConf = new GenericConfig(jaxbProtFiltConf.getName(), this, jaxbProtFiltConf.getInitParam());
                
                // try constructing the filter class
                @SuppressWarnings("unchecked")
                Class<ProtocolFilter> clazz = (Class<ProtocolFilter>) loader.loadClass(jaxbProtFiltConf.getClazz());

                // create the filter!
                ProtocolFilter newInst = clazz.newInstance();

                // init it
                newInst.init(filterConf);

                // and add it to our lists, depending on its config
                if (jaxbProtFiltConf.isIn()) { inFilters.add(newInst); }
                // NOTE! THE OUTGOING FILTERS NEED TO BE PROCESSED IN 
                // REVERSE ORDER, SO ALWAYS ADD OUT FILTERS TO THE FRONT
                if (jaxbProtFiltConf.isOut()) { outFilters.add(0, newInst); }

            }
            
            // we should have created a nice ProtocolFilter list for in and outgoing filters
            // so lets finally create the protocolchain.
            // note that we have a mapping for ProtocolName -> ProtocolChain
            this.protocols.put(jaxbProtConf.getName(), new ProtocolChain(inFilters, outFilters));
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
     * @throws      ShardletException  
     */
    @Override
    public GameAppBuildFinish shardlets(List<ShardletConfig> shardletConfigs) throws 
            ClassNotFoundException, 
            InstantiationException,
            IllegalAccessException,
            ShardletException
    {
        // Now, the last thing that's missing is the Shardlets of course
        // we can do that like we did with the protocol filters
        // here's what we'll need:
        // - get the shardlet list
        // - loop trough and create a generic config for it
        // - get the shardlet class
        // - create the shardlet and call init(config)
        // - add it to the aggregator and to our internal shardlet list
        shardlets = new ArrayList<>();
        
        for (ShardletConfig jaxbShardConf : shardletConfigs)
        {
            
            // to create a new shardlet, we need it's config
            // and it's class

            // create a new generic config out of the info
            // found in the deployment descriptor
            Config shardletConf = new GenericConfig(jaxbShardConf.getName(), this, jaxbShardConf.getInitParam());

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
     * Step.
     * 
     * @return 
     */
    @Override
    public GameAppContext finish() 
    {
        // TODO: check if we got some cleanup to do here
        return this;
    }
}
