/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container.events;

import com.gamerevision.rusty.realityshard.shardlet.EventHandler;


/**
 * Abstract base class for startup listeners.
 * 
 * @author _rusty
 */
public abstract class InternalEventListener 
{
    
    /**
     * Handler for the startup event, triggered when the container facade finished
     * creating all managers.
     * 
     * @param       event 
     */
    @EventHandler
    public abstract void handleStartupEvent(InternalStartupEvent event);
}
