/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * A class loader that is used to load the shardlet
 * classes from a GameApp directory.
 * Build upon an URL classloader
 * 
 * @author _rusty
 */
public final class GameAppClassLoaderFactory
{
    
    /**
     * Creates a new GameAppClassLoader
     * 
     * TODO: Clean up this class and implement it in a way that it
     * is actually usable.
     * 
     * @param       path                    The path to the GameApp folder.
     * @return      The newly created class loader that holds
     * @throws      MalformedURLException  
     */
    public static URLClassLoader produceGameAppClassLoader(File path) 
            throws MalformedURLException
    {
        // a "GAME-INF" GameApp folder should include
        // /lib/*.jar      - meaning any external 3rd party jar goes here
        // /classes/       - meaning any shardlet stuff goes there
        
        // so what we do is adding these dirs to the URL class loader
        
        List<URL> urls = new ArrayList<>();
        
        
        // search for all the libs
        // because the shardlet expects them to be there
        File lib = new File(path, "lib");
        
        if (lib.exists()) 
        {        
            // quick and dirty: get all jars from there (not including sub directories)
            File[] libs = lib.listFiles(new FileFilter() 
            {
                @Override
                public boolean accept(File file) 
                {
                    return file.getName().toLowerCase().endsWith(".jar");
                }
            });
        
            // add the found jars to the URL's
            for (File file : libs) 
            {
                urls.add(file.toURI().toURL());
            }
        }
        
        // create the URL that contains the "classes" folder
        // i guess this code is total crap
        urls.add(new File(path, "classes").toURI().toURL());

                
        // some more horrifc code follows
        return new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getSystemClassLoader());
        
    }
    
}
