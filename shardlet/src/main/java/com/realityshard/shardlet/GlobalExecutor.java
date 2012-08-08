/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;

import java.util.concurrent.ScheduledExecutorService;


/**
 * This class provides access to the global executor object (a thread pool manager)
 * you can trust the host application to set the executor at startup.
 * 
 * Any exception possibly thrown by any method below is not expected to be thrown
 * at any time because of the first statement.
 * 
 * @author _rusty
 */
public class GlobalExecutor 
{
    
    private static ScheduledExecutorService executor = null;
    
    /**
     * Initialize the static executor value of this class.
     * 
     * @param       executor                The global executor.
     */
    public static void init(ScheduledExecutorService initExec)
    {
        executor = initExec;
    }
    
    /**
     * Getter.
     * 
     * @return      The global executor as set by the host application.
     * @throws      IllegalStateException   If no executor was set.
     */
    public static ScheduledExecutorService get()
            throws IllegalStateException
    {
        if (executor == null)
        {
            throw new IllegalStateException("The global executor was not yet set by the host application!");
        }
        
        return executor;
    }
}
