/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container.events;

import com.gamerevision.rusty.realityshard.shardlet.ActionEvent;
import com.gamerevision.rusty.realityshard.shardlet.ShardletAction;


/**
 * Triggered when a new packet arrives.
 * 
 * @author _rusty
 */
public final class InternalNewNetworkActionEvent extends GenericInternalEvent
    implements ActionEvent 
{
    
    private final ShardletAction newAction;
    
    
    /**
     * Constructor.
     * 
     * @param       newAction 
     */
    public InternalNewNetworkActionEvent(ShardletAction newAction)
    {
        this.newAction = newAction;
        
    }

    
    /**
     * Getter.
     * 
     * @return
     */
    @Override
    public ShardletAction getAction() 
    {
        return newAction;
    }
}
