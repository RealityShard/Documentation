/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.container.gameapp.GameAppContext;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildFinish 
{
    /**
     * Build step: finish building the game app
     * 
     * @return      Finally, the game app context object 
     */
    public GameAppContext finish();
}
