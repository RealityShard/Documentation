/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.intershardcom;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.GenericEventAction;
import com.realityshard.shardlet.ShardletContext;


/**
 * Triggered when the ShardletContext of a game-app has been created successfully.
 * This may be the time to load anything of a context-global scope into it.
 * 
 * @author _rusty
 */
public final class ParentContextEventAction extends GenericEventAction
{
    
    private final ShardletContext parent;
    
    
    /**
     * Constructor.
     * 
     * @param       parent                  The 'parent' game app context, that
     *                                      requested the creation of this Context
     *                                      (Meaning the one where this is event is thrown)
     */
    public ParentContextEventAction(ShardletContext parent)
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
