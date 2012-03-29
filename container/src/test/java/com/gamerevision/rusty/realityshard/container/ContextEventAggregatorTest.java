/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamerevision.rusty.realityshard.container;

import java.lang.reflect.InvocationTargetException;
import junit.framework.TestCase;

/**
 *
 * @author _rusty
 */
public class ContextEventAggregatorTest extends TestCase
{
    
    public ContextEventAggregatorTest(String name)
    {
        super(name);
    }
    
    public void testEvent()
    {
        // instantiate the listener and aggregator
        TestListenerOne listenerOne = new TestListenerOne();
        ContextEventAggregator aggr = new ContextEventAggregator();
        
        // add the listener
        aggr.addListener(listenerOne);
        
        // nao exeQt da fuq
        boolean errorThrown = false;
        try
        {
            aggr.triggerEvent(new TestEventOne());
        }
        catch (InvocationTargetException ex)
        {
            errorThrown = (ex.getTargetException().getClass().equals(UnsupportedOperationException.class));
            // that should have happened, because test listner throws the exception!
        }
        
        assert errorThrown;
    }
}
