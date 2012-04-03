/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.shardlet.events.context;

import com.gamerevision.rusty.realityshard.shardlet.ActionEvent;
import com.gamerevision.rusty.realityshard.shardlet.ShardletAction;
import com.gamerevision.rusty.realityshard.shardlet.events.GenericContextEvent;


/**
 * Triggered by the shardlet(s) when a new action is ready to be send to the client
 * 
 * @author _rusty
 */
public class ContextOutgoingActionEvent extends GenericContextEvent
    implements ActionEvent
{
    
    private final ShardletAction action;

    
    /**
     * Constructor.
     * 
     * @param       action                  The action that will be send to a network client.
     */
    public ContextOutgoingActionEvent(ShardletAction action)
    {
        this.action = action;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The action to the network client.
     */
    @Override
    public ShardletAction getAction() 
    {
        return action;
    }
}
