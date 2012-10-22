/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.utils;

import com.realityshard.shardlet.ConfigProtocolFilter;
import com.realityshard.shardlet.ConfigShardlet;
import com.realityshard.shardlet.ShardletContext;
import java.util.*;


/**
 * Used as a config implementation for protocol filters and shardlets
 * 
 * @author _rusty
 */
public class ConfigFactory implements 
        ConfigShardlet, 
        ConfigProtocolFilter
{
    
    /**
     * This class is used as a dataholder when creating a new config.
     */
    public static class DataContainer
    {
        private final String name;
        private final Map<String, String> params;
        
        public DataContainer(String name, Map<String, String> params)
        {
            this.name = name;
            this.params = params;
        }

        public String getName() {
            return name;
        }

        public Map<String, String> getParams() {
            return params;
        }
    }
    
    private final String name;
    private final Map<String, String> initParams;
    private final ShardletContext context;
    
    
    /**
     * Constructor.
     * 
     * @param       name                    Name of the filter
     * @param       context                 The context of this shardlet
     * @param       params                  InitParams of the filter
     */
    private ConfigFactory(String name, ShardletContext context, Map<String, String> params)
    {
        this.name = name;
        this.context = context;
        
        // try to add the init parameters
        initParams = new HashMap<>();
        initParams.putAll(params);
    }
    
    
    /**
     * Factory method.
     * 
     * @param       name                    Name of the filter
     * @param       params                  InitParams of the filter
     * @return      The config 
     */
    public static ConfigProtocolFilter produceConfigProtocolFilter(String name, List<com.realityshard.schemas.protocol.InitParam> params)
    {
        // try to add the init parameters
        Map<String, String> tmp = new HashMap<>();
        
        // kinda verbose here, but due to namespacing problems 
        // with jaxb we have to do this
        for (com.realityshard.schemas.protocol.InitParam param : params) 
        {
            tmp.put(param.getName(), param.getValue());
        }
        
        return new ConfigFactory(name, null, tmp);
    }
    
    
    /**
     * Factory method. (Choice)
     * 
     * @param       name                    Name of the filter
     * @param       params                  InitParams of the filter
     * @return      The config 
     */
    public static ConfigProtocolFilter produceConfigProtocolFilter(String name, Map<String, String> params)
    {
        return new ConfigFactory(name, null, params);
    }
    
    
    /**
     * Factory method.
     * 
     * @param       name                    Name of the shardlet
     * @param       context                 The context of this shardlet
     * @param       params                  InitParams of the shardlet
     * @return      The config. 
     */
    public static ConfigShardlet produceConfigShardlet(String name, ShardletContext context, List<com.realityshard.schemas.gameapp.InitParam> params)
    {
        // try to add the init parameters
        Map<String, String> tmp = new HashMap<>();
        
        // kinda verbose here, but due to namespacing problems 
        // with jaxb we have to do this
        for (com.realityshard.schemas.gameapp.InitParam param : params) 
        {
            tmp.put(param.getName(), param.getValue());
        }
        
        return new ConfigFactory(name, context, tmp);
    }
    
    
    /**
     * Factory method. (Choice)
     * 
     * @param       cont                    The data holder for name and init params
     * @param       context                 The context of this shardlet
     * @return      The config. 
     */
    public static ConfigShardlet produceConfigShardlet(ShardletContext context, DataContainer cont)
    {
        // try to add the init parameters
        Map<String, String> tmp = new HashMap<>();
        
        tmp.putAll(cont.getParams());
        
        return new ConfigFactory(cont.getName(), context, tmp);
    }

    
    /**
     * Getter.
     * 
     * @return      The name of the shardlet or filter that this config object is made for.
     */
    @Override
    public String getName()
    {
        return name;
    }


    /**
     * Getter.
     * This is returns null because filters bound to the context manager
     * dont have a specific shardlet context
     * 
     * @return      The context of all shardlets that this app has.
     */
    @Override
    public ShardletContext getShardletContext()
    {
        return context;
    }
    
    
    /**
     * Getter.
     * 
     * @param       name                    The name of the (generic) parameter.
     *                                      The object will try to find it based on that string.
     * @return      The parameter's value if found
     */
    @Override
    public String getInitParameter(String name)
    {
        return initParams.get(name);
    }

    
    /**
     * Getter.
     * 
     * @return      All parameter names (the keys without values)
     */
    @Override
    public Enumeration<String> getInitParameterNames()
    {
        return Collections.enumeration(initParams.keySet());
    }
}
