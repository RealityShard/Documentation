/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container.events;

import com.gamerevision.rusty.realityshard.shardlet.ActionEvent;
import com.gamerevision.rusty.realityshard.shardlet.ShardletAction;


/**
 * Triggered when the container produced a new network action (a packet)
 * So that it may be transfered to the network manager.
 * 
 * @author _rusty
 */
public final class InternalOutgoingNetworkActionEvent extends GenericInternalEvent
    implements ActionEvent 
{
    
    private final ShardletAction newAction;
    
    
    /**
     * Constructor.
     * 
     * @param       newAction 
     */
    public InternalOutgoingNetworkActionEvent(ShardletAction newAction)
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
