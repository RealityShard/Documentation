/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.schemas.Protocol;
import com.realityshard.shardlet.ShardletException;
import java.util.List;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildProtocols
{
    /**
     * Build step: create the protocol handlers
     * 
     * @param       protocols
     * @return      The next build step
     * @throws      ClassNotFoundException
     * @throws      InstantiationException
     * @throws      IllegalAccessException
     * @throws      ShardletException  
     */
    public GameAppBuildShardlets protocols(List<Protocol> protocols) throws 
            ClassNotFoundException, 
            InstantiationException,
            IllegalAccessException,
            ShardletException;
}
