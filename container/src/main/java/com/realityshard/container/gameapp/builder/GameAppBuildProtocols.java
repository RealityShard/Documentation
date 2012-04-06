/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.container.ProtocolChain;
import com.realityshard.schemas.Protocol;
import com.realityshard.shardlet.ShardletException;
import java.util.List;
import java.util.Map;


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
    public GameAppBuildShardlets setProtocols(List<Protocol> protocols) throws 
            ClassNotFoundException, 
            InstantiationException,
            IllegalAccessException,
            ShardletException;
    
    
    /**
     * Build step: set the protocol handlers directly
     * 
     * @param       protocols
     * @return      The next build step
     */
    public GameAppBuildShardlets setProtocols(Map<String, ProtocolChain> protocols);
}
