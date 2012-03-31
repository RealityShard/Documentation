/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.shardlet.events.container;

import com.gamerevision.rusty.realityshard.shardlet.events.GenericContainerEvent;


/**
 * Triggered when the container has encountered a fatal error that stops it from
 * executing any subroutines any longer.
 * Hint: I dont guarantee that the listeners are executed correctly if this event
 * occurs.
 * 
 * @author _rusty
 */
public final class ContainerCrashEvent extends GenericContainerEvent
{
    
}
