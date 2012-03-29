/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.EventListener;
import com.gamerevision.rusty.realityshard.shardlet.GenericShardlet;


/**
 *
 * @author _rusty
 */
public class TestShardlet extends GenericShardlet
{
    
    /**
     * Constructor should always be empty for shardlets
     */
    public TestShardlet() {}
    
    
    /**
     * Use this as a constructor replacement
     */
    @Override
    protected void init() 
    {
        // do some initial stuff here. this method will be called once the
        // container loads this shardlet
    }

    
    /**
     * This is a sample event handler
     * @param event 
     */
    @EventListener
    public void handleEventOne(TestEventOne event) 
    {
        // this will get triggered when the event "TestEventOne"
        // is triggered.
        
        // some sample test code:
        throw new UnsupportedOperationException("ServiceEventOne");
    }
    
    
    /**
     * This is a sample event handler
     * @param event 
     */
    @EventListener
    public void handleEventTwo(TestEventTwo event)
    {
        // this will get triggered when the event "TestEventTwo"
        // is triggered.
        
        // some sample test code:
        throw new UnsupportedOperationException("ServiceEventTwo");
    }


}
