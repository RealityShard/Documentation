/**
 * Distributed under the GNU GPL v.3
 */

package com.gamerevision.rusty.realityshard.shardlet.events;


/**
 * Triggered when the container is shutting down, and terminates all services.
 * Note that it will still call destroy on any registered Shardlets.
 * 
 * @author _rusty
 */
public final class ContainerShutdownEvent extends GenericContainerEvent
{
}
