/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container.events;

import com.gamerevision.rusty.realityshard.schemas.ServerConfig;


/**
 * Triggered when the container chooses to start up its internals.
 * Carries the server config file for anyone to have a look at.
 * 
 * @author _rusty
 */
public final class InternalStartupEvent extends GenericInternalEvent
{
   
    private final ServerConfig config;
    
    
    /**
     * Constructor.
     * 
     * @param       config                  The server config object
     */
    public InternalStartupEvent(ServerConfig config)
    {
        // save it for later use
        this.config = config;
    }
    
    
    /**
     * Getter.
     * Note: This is NOT threadsafe!
     * 
     * @return      The server configuration object.
     */
    public ServerConfig getServerConfig()
    {
        return config;
    }
}
