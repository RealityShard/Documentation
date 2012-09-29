/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.entitysystem;

import com.realityshard.shardlet.Event;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.util.List;


/**
 * Controls the EntitySystem.
 * Use this class to create new entities and manage the EntitySystem.
 * 
 * Note that an instance of this class will register a new EventAggregator.
 * 
 * @author _rusty
 */
public final class EntitySystemFacade 
{
    
    private final EventAggregator aggregator;
    private final ComponentManager compman;
    
    
    /**
     * Constructor.
     */
    public EntitySystemFacade()
    {
        // start the aggregator
        // NEW: this can now be done by the Aggregator constructor itself
        aggregator = new ConcurrentEventAggregator();
        
        compman = new ComponentManager(aggregator);
    }
    
    
    /**
     * Register a new entity.
     * Note: It is recommended to use a class that is derived from GenericEntity.
     * (Or in most cases, GenericEntity is just fine too)
     * 
     * @param       name                    The name of the entity-type.
     *                                      See the entity builders or XML file
     *                                      (or whatever we use to describe entities)
     * @return      The newly created entity, or null if nothing has been created.
     */
    public void registerEntity(Entity entity, List<AttributeComponent> attribs, List<BehaviourComponent> behavs)
    {
        // register the single components with the component manager
        
        for (AttributeComponent attr : attribs) 
        {
            // initialize (step 2) here?
            // nope. compman will initialize the components when they are given to it.
            compman.registerAttribute(entity, attr);
        }
        
        for (BehaviourComponent behav : behavs)
        {
            compman.registerBehaviour(entity, behav);
        }
    }
    
    
    /**
     * Removes an entity and deletes all its components.
     * 
     * This is done by searching for the components linked to that entity,
     * and then deleting them from the aggregator and compman (compman actually
     * does all this automatically)
     * 
     * @param       entity                  The entity reference that we will use to delete the entity
     */
    public void unregisterEntity(Entity entity)
    {
        compman.unregisterComponents(entity);
    }
    
    
    /**
     * Trigger an event within the Entity-System.
     * 
     * This is the only interface to the entities of the system!
     * 
     * THIS IS NOT YET IMPLEMENTED. PLEASE REMOVE THIS LINE IF THIS ACTUALLY WORKS.
     * 
     * @param       entity                  The entity that this event concerns (or null if it concerns all entities)
     * @param       event                   The event that you want to trigger within the E/S
     */
    public void triggerEntityEvent(Event event)
    {
        //TODO
    }
}
