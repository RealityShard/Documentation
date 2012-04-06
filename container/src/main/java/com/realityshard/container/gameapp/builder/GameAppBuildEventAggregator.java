/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.shardlet.EventAggregator;


/**
 * Part of the GameAppFluentBuilder
 * 
 * @author _rusty
 */
public interface GameAppBuildEventAggregator 
{
    
    /**
     * Build step: add the event aggregator that this game app will use
     * 
     * @param       aggregator
     * @return      The next step.
     */
    public GameAppBuildClassloader setAggregator(EventAggregator aggregator);
}
