/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.shardlet;

import com.gamerevision.rusty.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import org.junit.Test;


/**
 * JUnit test class
 * 
 * @author _rusty
 */
public class ConcurrentEventAggregatorTest extends TestCase
{
    
    private enum TestType {STRESS1, STRESS2, END}
    
    private EventAggregator aggr;
    
    private boolean testSuccessful = false;
    private int testCounter = 0;
    
    private TestType type;
    
    
    public ConcurrentEventAggregatorTest(String name)
    {
        super(name);
    }
    
    @Test
    public void testEvent()
    {
        // instantiate the executor and aggregator
        Executor executor = Executors.newFixedThreadPool(12);
        aggr = new ConcurrentEventAggregator(executor);
        
        // instantiate the shardlet and initilize it
        @SuppressWarnings("unchecked")
        Class<TestShardletInterface> testShardletClass = null;
        try 
        {
            testShardletClass = (Class<TestShardletInterface>) Class.forName("com.gamerevision.rusty.realityshard.shardlet.TestShardlet");
        } 
        catch (ClassNotFoundException ex) 
        {
            assert false;
        }
        
        // temporary workaround
        TestShardletInterface testShardlet = null;
        try 
        {
            testShardlet = testShardletClass.newInstance();
        } 
        catch (InstantiationException | IllegalAccessException ex) 
        {
            assert false;
        }
        
        testShardlet.setAggregator(aggr);
        
        
        // add the listener
        aggr.addListener(testShardlet);
        aggr.addListener(this);
        
        // nao exeQt da fuq
        // STRESS1 re-uses the event reference
        eventChaining(1, 10000000); //10mio
        
        // 10mio 10000000
        // 1mio  1000000
        
        // results:
        // (1, 10mio) takes roughly
        
        //assert false;
    }
    
    
    @EventHandler
    public void handleEventTwo(TestEventTwo event)
    {
        // when this gets executed, TestShardlet must have triggered the event
        if (type == TestType.STRESS1) 
        {
            testCounter++;
            aggr.triggerEvent(new TestEventOne());
        }
        else if (type == TestType.STRESS2) 
        {
        }
        else if (type == TestType.END)
        {
        }
    }
    
    
    /**
     * Invoke a chain of 'ch' events at once
     * @param x 
     */
    private void eventChaining(int ch, int x)
    {
        testCounter = 0;
        
        // simulate ch parallel event-chains
        for (int i = 0; i < ch; i++) 
        {
            aggr.triggerEvent(new TestEventOne());
        }
        
        // wait till the responses arrived
        while (testCounter < x)
        {
            try 
            {
                Thread.currentThread().sleep(1);
            } 
            catch (InterruptedException ex) 
            {
                assert false;
            }
        }

        // simply interrupt the execution ;D
        type = TestType.STRESS2;
    }
}
