/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.container.DevelopmentEnvironment;
import com.realityshard.container.NetworkAdapter;
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
    public interface BuildNetworkAdapter
    {
        public DetermineEnvironment useAdapter(NetworkAdapter adapter);
    }
    
    
    /**
     * Build step: 
     * setup development environment
     * or set server config of the production environment
     * 
     * Note:
     * The development env was introduced to be able to run the container 
     * without the need of deploying class files. 
     * This feature should not be used within the final remote server environment.
     */
    public interface DetermineEnvironment
    {
        public BuildGameAppStartup useDevelopmentEnvironment(DevelopmentEnvironment devel);
        public BuildProtocolSchemaFile useProductionEnvironment(ServerConfig config);
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
