/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.events.HeartBeatEvent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * This class is used to create periodically reoccuring HeartBeat events
 * 
 * @author _rusty
 */
public class Pacemaker implements Runnable
{
    
    private final ScheduledExecutorService excecutor;
    private final EventAggregator outputEventAggregator;
    private final int milliSecondTimeIntervall;
    
    private ScheduledFuture<?> future;
    private long lastInvocation = 0;
    
    
    /**
     * Constructor.
     * 
     * @param       executor                The scheduler that will trigger the pacemaker
     *                                      after a certain amount of time (2nd param)
     * @param       outputEventAggregator   
     * @param       milliSecondTimeIntervall 
     */
    public Pacemaker(ScheduledExecutorService executor, EventAggregator outputEventAggregator, int milliSecondTimeIntervall)
    {
        this.excecutor = executor;
        this.outputEventAggregator = outputEventAggregator;
        this.milliSecondTimeIntervall = milliSecondTimeIntervall;
    }
    
    
    /**
     * Starts the pacemaker
     */
    public void start()
    {
        lastInvocation = System.currentTimeMillis();
        future = excecutor.scheduleAtFixedRate(this, milliSecondTimeIntervall, milliSecondTimeIntervall, TimeUnit.MILLISECONDS);
    }
    
    
    /**
     * Stops the pacemaker
     */
    public void stop()
    {
        future.cancel(true);
    }

    
    /**
     * Does nothing else than trigger the HeartBeatEvent.
     * The scheduler will execute this task after a certain amount
     * of time, given at instantiation time.
     */
    @Override
    public void run() 
    {
        outputEventAggregator.triggerEvent(
                new HeartBeatEvent((int)(System.currentTimeMillis()-lastInvocation)));
    }
}
