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
 * @author _rusty
 */
public final class EntitySystemFacade 
{
    
    private final EventAggregator aggregator;
    private final ComponentManager compman;
    
    
    /**
     * Constructor.
     * 
     * @param       executor                The global thread pool manager. Use this
     *                                      parameter to avoid flooding by new-thread-creation.
     */
    public EntitySystemFacade()
    {
        // start the aggregator
        aggregator = new ConcurrentEventAggregator();
        
        compman = new ComponentManager(aggregator);
    }
    
    
    /**
     * Creates a new entity and returns it's identifier
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
     * Note that the builder that created the entity will decide on how to delete it
     * correctly. (Because the builder knows what the entity consists of, and where
     * it needs to delete / unsubscribe etc.)
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
     * @param       entity                  The entity that this event concerns (or null if it concerns all entities)
     * @param       event                   The event that you want to trigger within the E/S
     */
    public void triggerEntityEvent(Event event)
    {
        //TODO
    }
}
