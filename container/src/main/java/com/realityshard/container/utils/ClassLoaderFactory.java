/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.utils;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


/**
 * A class loader that is used to load the shardlet
 * classes from a GameApp directory.
 * Build upon an URL classloader
 * 
 * TODO: Both static factory methods use the same algorithm! WTF?!
 * 
 * @author _rusty
 */
public final class ClassLoaderFactory
{
    
    /**
     * Creates a new protocol ClassLoader
     * 
     * TODO: Include resources
     * TODO: Clean up this class and implement it in a way that it
     * is actually usable.
     * 
     * @param       path                    The path to the PROT-INF folder.
     * @param       parent                  The parent class loader 
     *                                      (will be used for class lookup first)
     * @return      The newly created class loader  
     * @throws      MalformedURLException  
     */
    public static ClassLoader produceProtocolClassLoader(File path, ClassLoader parent) 
            throws MalformedURLException
    {
        // a "PROT-INF" protocol folder should include
        // /lib/*.jar      - meaning any external 3rd party jar goes here
        // /classes/       - meaning any protocol filter stuff goes there
        // /ressource/     - TODO: all resources go here 
        
        // so what we do is adding these dirs to the URL class loader
        
        // this list will contain the URLS later on
        // (those that we need to give to the URL class loader
        List<URL> urls = new ArrayList<>();
        
        
        // search for all the libs
        // because the protocol filter expects them to be there
        // (we need to include their paths directly within the URLClassLoader,
        //  as it can load the classes implicitly then)
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
        // we must assume that classes from here are never packed into jars
        // else i guess they couldnt be loaded that way
        urls.add(new File(path, "classes").toURI().toURL());

                
        // TODO: Check if this is the right way to do it:
        return new URLClassLoader(urls.toArray(new URL[0]), parent);
        
    }
    
    
    /**
     * Creates a new GameApp ClassLoader
     * 
     * TODO: Include resources
     * TODO: Clean up this class and implement it in a way that it
     * is actually usable.
     * 
     * @param       path                    The path to the GAME-INF folder.
     * @param       parent                  The parent class loader 
     *                                      (will be used for class lookup first)
     * @return      The newly created class
     * @throws      MalformedURLException  
     */
    public static ClassLoader produceGameAppClassLoader(File path, ClassLoader parent) 
            throws MalformedURLException
    {
        // a "GAME-INF" GameApp folder should include
        // /lib/*.jar      - meaning any external 3rd party jar goes here
        // /classes/       - meaning any shardlet stuff goes there
        // /ressource/     - TODO: all resources go here 
        
        // so what we do is adding these dirs to the URL class loader
        
        // this list will contain the URLS later on
        // (those that we need to give to the URL class loader
        List<URL> urls = new ArrayList<>();
        
        
        // search for all the libs
        // because the shardlet expects them to be there
        // (we need to include their paths directly within the URLClassLoader,
        //  as it can load the classes implicitly then)
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
        // we must assume that classes from here are never packed into jars
        // else i guess they couldnt be loaded that way
        urls.add(new File(path, "classes").toURI().toURL());

                
        // TODO: Check if this is the right way to do it:
        return new URLClassLoader(urls.toArray(new URL[0]), parent);
        
    }
    
}
