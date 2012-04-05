/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import java.util.concurrent.ScheduledExecutorService;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildPacemaker 
{
    /**
     * Build step: build the pacemaker.
     * 
     * @param       executor                The executor used to run the pacemaker
     * @param       milliseconds            The time intervall that needs to have passed till 
     *                                      the game app will be getting the next
     *                                      HeartBeatEvent from the pacemaker.
     * @return      The next build step.
     */
    public GameAppBuildInitParams heartBeat(ScheduledExecutorService executor, int milliseconds);
}
