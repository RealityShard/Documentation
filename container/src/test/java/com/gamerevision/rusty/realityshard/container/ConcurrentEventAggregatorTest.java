/**
 * Distributed under the GNU GPL v.3
 */

package com.gamerevision.rusty.realityshard.container;

import java.lang.reflect.InvocationTargetException;
import junit.framework.TestCase;


/**
 * JUnit test class
 * 
 * @author _rusty
 */
public class ConcurrentEventAggregatorTest extends TestCase
{
    
    public ConcurrentEventAggregatorTest(String name)
    {
        super(name);
    }
    
    public void testEvent()
    {
        // instantiate the listener and aggregator
        TestShardlet listenerOne = new TestShardlet();
        ConcurrentEventAggregator aggr = new ConcurrentEventAggregator();
        
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
