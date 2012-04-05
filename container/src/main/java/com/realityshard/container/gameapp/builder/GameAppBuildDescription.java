/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildDescription 
{
    /**
     * Build step: add the description of this game app
     * 
     * @param       description
     * @return      The next build step.
     */
    public GameAppBuildPacemaker description(String description);
}
