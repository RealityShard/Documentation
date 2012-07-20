/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.events;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.GenericEventAction;
import com.realityshard.shardlet.ShardletContext;


/**
 * This Event is triggered when the ShardletContext of a game-app 
 * has been created successfully, meaning all Shardlets have been initialized.
 * 
 * This may be the right time to distribute any context-global references.
 * 
 * @author _rusty
 */
public final class GameAppCreatedEvent extends GenericEventAction
{
    
    private final ShardletContext parent;
    
    
    /**
     * Constructor.
     * 
     * @param       parent                  The 'parent' game app context, that
     *                                      requested the creation of this Context
     *                                      (Meaning the one where this is event is thrown)
     */
    public GameAppCreatedEvent(ShardletContext parent)
    {
        this.parent = parent;
        
    }
    
    
    /**
     * Getter.
     * 
     * @return      The parent-context that instructed the R:S server to create this context
     *              (Meaning the context where this event is triggered)
     */
    public ShardletContext getParent() 
    {
        return parent;
    }
    

    /**
     * Trigger the event that this Action contains
     * 
     * @param       aggregator              The EventAggregator that you want an
     *                                      this action to be published on
     */
    @Override
    public void triggerEvent(EventAggregator aggregator)
    {
        aggregator.triggerEvent(this);
    }
}
