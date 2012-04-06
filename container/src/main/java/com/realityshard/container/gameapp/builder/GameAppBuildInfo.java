/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.schemas.AppInfo;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildInfo extends 
        GameAppBuildName,
        GameAppBuildDescription,
        GameAppBuildPacemaker,
        GameAppBuildInitParams
{
    /**
     * Build step: add the info
     * 
     * @param       info
     * @param       executor 
     * @return      The next build step
     */
    public GameAppBuildProtocols setInfo(AppInfo info, ScheduledExecutorService executor);
}
