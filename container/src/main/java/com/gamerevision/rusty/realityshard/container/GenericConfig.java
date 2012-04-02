/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.schemas.InitParam;
import com.gamerevision.rusty.realityshard.shardlet.Config;
import com.gamerevision.rusty.realityshard.shardlet.ShardletContext;
import java.util.*;


/**
 * This is an implementation of shardlet.Config, which is used to
 * configurate the shardlets or filters of a game app
 * 
 * @author _rusty
 */
public class GenericConfig implements Config
{
    
    private final String name;
    private final ShardletContext context;
    private final Map<String, String> initParams;
    
    
    /**
     * Constructor.
     * 
     * @param       name                    Name of the shardlet or filter
     * @param       context                 The context of this shardlet or filter
     * @param       params                  InitParams of the shardlet or filter
     */
    public GenericConfig(String name, ShardletContext context, List<InitParam> params)
    {
        this.name = name;
        this.context = context;
        
        // try to add the init parameters
        initParams = new HashMap<>();
        
        for (InitParam param : params) 
        {
            initParams.put(param.getName(), param.getValue());
        }
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
