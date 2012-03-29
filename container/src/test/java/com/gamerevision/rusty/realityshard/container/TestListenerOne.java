/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.EventListener;


/**
 *
 * @author _rusty
 */
public class TestListenerOne 
{

    @EventListener
    public void service(TestEventOne event) 
    {
        throw new UnsupportedOperationException("ServiceEventOne");
    }
    
    @EventListener
    public void service(TestEventTwo event)
    {
        throw new UnsupportedOperationException("ServiceEventTwo");
    }
}
