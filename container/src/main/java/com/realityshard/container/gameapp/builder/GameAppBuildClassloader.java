/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildClassloader 
{
    /**
     * Build step: add the setClassloader for this game app
     * 
     * @param       loader
     * @return      The next build step.
     */
    public GameAppBuildInfo setClassloader(ClassLoader loader);
}
