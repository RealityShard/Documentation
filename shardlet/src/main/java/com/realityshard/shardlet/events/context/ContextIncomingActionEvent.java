/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.events.context;

import com.realityshard.shardlet.ActionEvent;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.events.GenericContextEvent;


/**
 * Triggered when the context recieved a new event
 * 
 * @author _rusty
 */
public class ContextIncomingActionEvent extends GenericContextEvent
    implements ActionEvent
{
    
    private final ShardletAction action;

    
    /**
     * Constructor.
     * 
     * @param       action                  The action send by a network client.
     */
    public ContextIncomingActionEvent(ShardletAction action)
    {
        this.action = action;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The action from the network client.
     */
    @Override
    public ShardletAction getAction() 
    {
        return action;
    }

}
