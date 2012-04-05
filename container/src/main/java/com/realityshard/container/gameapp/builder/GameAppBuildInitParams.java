/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.schemas.InitParam;
import java.util.List;
import java.util.Map;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildInitParams 
{
    /**
     * Build step: save the init parameters.
     * 
     * @param       mandatoryParams 
     * @param       additionalParams
     * @return      The next build step.
     */
    public GameAppBuildProtocols initParams(List<InitParam> mandatoryParams, Map<String, String> additionalParams);
}
