/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.entitysystem;

import com.realityshard.shardlet.EventAggregator;


/**
 * A Behaviour is an updateable component. It can also react to
 * events depending on its implementation.
 * 
 * @author _rusty
 */
public interface BehaviourComponent
{
    
    /**
     * This will be called by the component manager upon registering a component.
     * 
     * Use this method for gathering the references that your component still needs,
     * and for registering your component with the event aggregator.
     * 
     * CAUTION! In this context, components should be registering themselves with 
     * the aggregator on their own, because only they will know what events they
     * want to handle and in which context.
     * 
     * @param       compman                 The component manger of the entity system
     * @param       aggregator              The event aggregator of the entity system
     */
    public void init(ComponentManager compman, EventAggregator aggregator);
    

    /**
     * Updates this behaviour component.
     * 
     * @param       deltaTime               The time that passed between now an the
     *                                      last invocation of this method.
     */
    public void update(int deltaTime);
}
