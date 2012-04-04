/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.host;

import com.gamerevision.rusty.realityshard.container.ContainerFacade;
import com.gamerevision.rusty.realityshard.network.ConcurrentNetworkManager;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * This program creates the objects necessary to start the server
 * 
 * TODO: implement functionality, clean up
 * 
 * @author _rusty
 */
public final class HostApplication 
{
    /**
     * Runs the application.
     * 
     * @param       args 
     */
    public static void main(String[] args)
    {
        // TODO process the args ;D
        
        // make this variable
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(12);
        
        // this is not implemented to the interface, but thats actually necessary here
        // though still, fix this later
        ConcurrentNetworkManager netMan = new ConcurrentNetworkManager();
        executor.scheduleAtFixedRate(netMan, 0, 5, TimeUnit.MILLISECONDS);
        
        try 
        {
            // create the container
            ContainerFacade container = new ContainerFacade(netMan, executor, "", "");
        } 
        catch (Exception ex) 
        {
            // TODO logging
            System.exit(1);
        }
    }
}
