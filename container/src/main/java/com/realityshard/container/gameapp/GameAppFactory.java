/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import java.util.Map;


/**
 *
 * @author _rusty
 */
public interface GameAppFactory 
{

    /**
     * The name of the game app should be unique, so it is identifiable by the
     * ContextManager.
     * 
     * @return      The name of the GameApp this factory produces.
     */
    public String getName();
    
    
    /**
     * Indicates whether a game app should be loaded after the container started.
     * Note that only one game app per factory that has this startup indicator, will
     * be created.
     * 
     * @return      The boolean value that determines whether one game app of this
     *              factory should be produced when the container starts
     */
    public boolean isStartup();
    
    
    /**
     * Create a new Game-App and return its context.
     * 
     * @param       manager                 The context manager that holds this game app.
     * @param       additionalParams        The additional parameters used for game app creation.
     * @return      The new GameApp as a shardlet.ShardletContext
     * @throws      Exception               If we could not create the game app just like that.
     */
    public GameAppContext produceGameApp(ContextManager manager, Map<String, String> additionalParams)
            throws Exception;

}
