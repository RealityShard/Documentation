/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.shardlet.gameapp;

import com.gamerevision.rusty.realityshard.shardlet.EventHandler;
import com.gamerevision.rusty.realityshard.shardlet.GenericShardlet;
import com.gamerevision.rusty.realityshard.shardlet.events.context.ContextIncomingActionEvent;
import com.gamerevision.rusty.realityshard.shardlet.events.context.ContextOutgoingActionEvent;


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
        
        publishEvent(new ContextOutgoingActionEvent(event.getAction()));
    }

}
