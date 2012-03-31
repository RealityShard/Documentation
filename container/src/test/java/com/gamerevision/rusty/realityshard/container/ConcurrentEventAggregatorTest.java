/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.EventHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;


/**
 * JUnit test class
 * 
 * @author _rusty
 */
public class ConcurrentEventAggregatorTest extends TestCase
{
    private boolean testSuccessful = false;
    
    public ConcurrentEventAggregatorTest(String name)
    {
        super(name);
    }
    
    public void testEvent()
    {
        // instantiate the executor and aggregator
        Executor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
        ConcurrentEventAggregator aggr = new ConcurrentEventAggregator(executor);
        
        // instantiate the shardlet and initilize it
        TestShardlet testShardlet = new TestShardlet();
        
        // temporary workaround
        testShardlet.setAggregator(aggr);
        
        
        // add the listener
        aggr.addListener(testShardlet);
        aggr.addListener(this);
        
        // nao exeQt da fuq
        aggr.triggerEvent(new TestEventOne());
        
        // wait till the aggregator has invoked our own method
        // or till 10 seconds are over (timeout)
        int counter = 0;
        while (!testSuccessful && counter <= 10)
        {
            counter++;
            
            try 
            {
                Thread.currentThread().sleep(1000);
            } 
            catch (InterruptedException ex) 
            {
                assert false;
            }
        }
        
        assert testSuccessful;
    }
    
    @EventHandler
    public void handleEventTwo(TestEventTwo event)
    {
        // when this gets executed, TestShardlet must have triggered the event
        testSuccessful = true;
    }
}
