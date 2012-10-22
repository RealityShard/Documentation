/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import com.realityshard.schemas.gameapp.GameApp;
import java.io.File;


/**
 * Used for data storage only, and therefore left uncommented
 * This class stores data used to create a game app.
 * It does not store an already created game app!
 * 
 * @deprecated TODO: This needs to be updated.
 * @author _rusty
 */
public final class GameAppInfo 
{
    private final String name;
    private final File path;
    private final GameApp config;

    public GameAppInfo(String name, File path, GameApp config) 
    {
        this.name = name;
        this.path = path;
        this.config = config;
    }

    public GameApp getConfig() 
    {
        return config;
    }

    public String getName() 
    {
        return name;
    }

    /**
     * Getter.
     *
     * @return The path to the GAME-INF of this game app
     */
    public File getPath() 
    {
        return path;
    }

}
