/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.shardlet;


/**
 *
 * @author _rusty
 */
public class TestShardlet extends GenericShardlet implements TestShardletInterface
{
    private EventAggregator aggr;

    
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
     * temporary fake method
     */
    @Override
    public void setAggregator(EventAggregator aggr)
    {
        this.aggr = aggr;
    }
    
    /**
     * This is a sample event handler
     * 
     * @param event 
     */
    @EventHandler
    public void handleEventOne(TestEventOne event) 
    {
        // this will get triggered when the event "TestEventOne"
        // is triggered.

        // some sample test code:
        aggr.triggerEvent(new TestEventTwo());
    }
}
