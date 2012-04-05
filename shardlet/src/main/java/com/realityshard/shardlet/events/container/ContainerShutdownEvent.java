/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.events.container;

import com.realityshard.shardlet.events.GenericContainerEvent;


/**
 * Triggered when the container is shutting down, and terminates all services.
 * Note that it will still call destroy on any registered Shardlets.
 * 
 * @author _rusty
 */
public final class ContainerShutdownEvent extends GenericContainerEvent
{
}
