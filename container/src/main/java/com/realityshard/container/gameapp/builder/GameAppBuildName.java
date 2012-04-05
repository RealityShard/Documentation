/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildName 
{
    /**
     * Build step: add the name
     * 
     * @param       name
     * @return      The next build step
     */
    public GameAppBuildDescription name(String name);
}
