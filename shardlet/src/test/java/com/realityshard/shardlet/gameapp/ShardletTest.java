/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.gameapp;

import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.events.context.ContextIncomingActionEvent;


/**
 * Does nothing, just for testing
 * 
 * @author _rusty
 */
public class ShardletTest extends GenericShardlet
{

    @Override
    protected void init() 
    {
        //do nothing
    }
    
    // some random handler
    @EventHandler
    public void handleIncoming(ContextIncomingActionEvent event)
    {
        event.getAction().setAttribute("ProtocolFilter.test", "Shardlet was here!");
        
        sendAction(event.getAction());
    }

}
