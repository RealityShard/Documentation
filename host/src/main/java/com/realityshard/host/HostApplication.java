/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.host;

import com.realityshard.container.ContainerFacade;
import com.realityshard.network.ConcurrentNetworkManager;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
        Logger logger = LoggerFactory.getLogger(HostApplication.class);
        
        File localPath = new File(HostApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        
        File configPath = new File(localPath, "Config");
        File schemaPath = new File(localPath, "Schema");
        File gameAppsPath = new File(localPath, "GameApps");
        
        logger.info("Host application starting up...");
        
        // TODO process the args ;D
        
        
        // make this variable
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(12);
        
        // this is not implemented to the interface, but thats actually necessary here
        ConcurrentNetworkManager netMan = new ConcurrentNetworkManager();
        executor.scheduleAtFixedRate(netMan, 0, 5, TimeUnit.MILLISECONDS);
        
        try 
        {
            // create the container
            ContainerFacade container = new ContainerFacade(netMan, executor, configPath, schemaPath, gameAppsPath);
        } 
        catch (Exception ex) 
        {
            logger.error("Container failed to start up.", ex);
            System.exit(1);
        }
    }
}
