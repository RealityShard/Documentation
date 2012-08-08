/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.entitysystem;

import com.realityshard.shardlet.EventAggregator;


/**
 * This interface defines how classes should look like, that implement a strategy 
 * to create and register an entity with this EntitySystem
 * 
 * @author _rusty
 */
public interface EntityBuildingStrategy 
{
    
    /**
     * This method will be called by the EntitySystemFacade when the user wants to
     * register an entity.
     * 
     * @param       manager                 The component manager of the entity system
     * @param       aggregator              The entity event aggregator of the entity system
     * @return      The entity that has been registered with the component manager and event aggregator
     */
    public Entity createEntity(ComponentManager manager, EventAggregator aggregator);
}
