/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.container.ContainerFacade;
import com.realityshard.container.gameapp.ContextManager;
import com.realityshard.schemas.serverconfig.ServerConfig;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Provides the grammar for building steps used in the creation
 * of a ContextManager
 * 
 * @author _rusty
 */
public interface ContextManagerBuildDescriptors 
{

    /**
     * Build step: set facade
     */
    public interface BuildFacade
    {
        public BuildExecuter useFacade(ContainerFacade facade);
    }
    
    
    /**
     * Build step: set excuter
     */
    public interface BuildExecuter
    {
        public BuildServerConfig useExecuter(ScheduledExecutorService executor);
    }
    
    
    /**
     * Build step: set server config
     */
    public interface BuildServerConfig
    {
        public BuildProtocolSchemaFile useServerConfig(ServerConfig config);
    }
    
    
    /**
     * Build step: set path to game app schema
     */
    public interface BuildProtocolSchemaFile
    {
        public BuildGameAppSchemaFile useProtocolSchema(File protocolSchemaFile);
    }
    
    
    /**
     * Build step: set path to game app schema
     */
    public interface BuildGameAppSchemaFile
    {
        public BuildProtocols useGameAppSchema(File gameAppSchemaFile);
    }
    
    
    /**
     * Build step: get the protocols from specified path
     */
    public interface BuildProtocols
    {
        public BuildGameApps setupProtocolsFromPath(File protcolsPath)
                throws Exception;
    }
    
    
    /**
     * Build step: get the protocol paths
     */
    public interface BuildGameApps
    {
        public BuildGameAppStartup setupGameAppsFromPath(File gameAppsPath)
            throws Exception;
    }
    
    
    /**
     * Build step: get the game app paths
     */
    public interface BuildGameAppStartup
    {
        public Build startupGameApps()
            throws Exception;
    }
    
    
    /**
     * Build step: finish this building process
     */
    public interface Build
    {
        public ContextManager build();
    }
}
