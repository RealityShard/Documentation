/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.gameapp;

import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.ShardletActionVerifier;
import com.realityshard.shardlet.events.context.ContextIncomingActionEvent;
import com.realityshard.shardlet.events.network.NetworkClientConnectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Does nothing, just for testing
 * 
 * @author _rusty
 */
public class ShardletTest extends GenericShardlet
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ShardletTest.class);

    
    @Override
    protected void init() 
    {
        LOGGER.debug("Shardlet initialized!");
        
        // fake always accept verifier
        ShardletActionVerifier verf = new ShardletActionVerifier() {

            @Override
            public boolean check(ShardletAction action) 
            {
                LOGGER.debug("Client verified ;D");
                return true;
            }
        };
        
        getShardletContext().addClientVerifier(verf, true);
    }
    
    
    // some random handler
    @EventHandler
    public void handleIncoming(ContextIncomingActionEvent event)
    {
        LOGGER.debug("Incoming action!");
        
        event.getAction().setAttribute("ProtocolFilter.test", "Shardlet was here!");
        
        sendAction(event.getAction());
    }

    
    // some random handler
    @EventHandler
    public void handleNewClient(NetworkClientConnectedEvent event)
    {
        LOGGER.debug("Incoming client!");
        
        event.getAction().setAttribute("ProtocolFilter.test", "Shardlet was here!");
        
        sendAction(event.getAction());
    }
}
