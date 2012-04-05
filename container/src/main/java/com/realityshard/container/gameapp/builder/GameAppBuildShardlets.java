/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.schemas.ShardletConfig;
import com.realityshard.shardlet.ShardletException;
import java.util.List;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildShardlets 
{
    /**
     * Build step: create the shardlets
     * 
     * @param       shardletConfigs
     * @return      The next build step.
     * @throws      ClassNotFoundException
     * @throws      InstantiationException
     * @throws      IllegalAccessException
     * @throws      ShardletException  
     */
    public GameAppBuildFinish shardlets(List<ShardletConfig> shardletConfigs) throws 
            ClassNotFoundException, 
            InstantiationException,
            IllegalAccessException,
            ShardletException;
}
