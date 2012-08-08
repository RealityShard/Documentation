/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.gameapp.builder;

import com.realityshard.container.NetworkAdapter;
import com.realityshard.container.gameapp.ContextManager;
import com.realityshard.container.gameapp.builder.ContextManagerBuildDescriptors.Build;
import com.realityshard.container.gameapp.builder.ContextManagerBuildDescriptors.BuildGameAppSchemaFile;
import com.realityshard.container.gameapp.builder.ContextManagerBuildDescriptors.BuildGameAppStartup;
import com.realityshard.container.gameapp.builder.ContextManagerBuildDescriptors.BuildGameApps;
import com.realityshard.container.gameapp.builder.ContextManagerBuildDescriptors.BuildNetworkAdapter;
import com.realityshard.container.gameapp.builder.ContextManagerBuildDescriptors.BuildProtocolSchemaFile;
import com.realityshard.container.gameapp.builder.ContextManagerBuildDescriptors.BuildProtocols;
import com.realityshard.container.gameapp.builder.ContextManagerBuildDescriptors.BuildServerConfig;
import com.realityshard.container.utils.ClassLoaderFactory;
import com.realityshard.container.utils.ConfigFactory;
import com.realityshard.container.utils.JaxbUtils;
import com.realityshard.container.utils.ProtocolChain;
import com.realityshard.schemas.gameapp.GameApp;
import com.realityshard.schemas.gameapp.Start;
import com.realityshard.schemas.protocol.Protocol;
import com.realityshard.schemas.protocol.ProtocolFilterConfig;
import com.realityshard.schemas.serverconfig.ServerConfig;
import com.realityshard.shardlet.ConfigProtocolFilter;
import com.realityshard.shardlet.ProtocolFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


/**
 * This class is used to build a new Context Manager
 * 
 * @author _rusty
 */
public class ContextManagerFluentBuilder extends ContextManager implements
        BuildNetworkAdapter,
        BuildServerConfig,
        BuildProtocolSchemaFile,
        BuildGameAppSchemaFile,
        BuildProtocols,
        BuildGameApps,
        BuildGameAppStartup,
        Build
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ContextManagerFluentBuilder.class);
    

    /**
     * Constructor.
     */
    private ContextManagerFluentBuilder()
    {
        // private means
        // this will keep people from messing with this class.
        super();
    }
    
    
    /**
     * Initiate the building process
     * 
     * @return 
     */
    public static BuildNetworkAdapter start()
    {
        return new ContextManagerFluentBuilder();
    }
    
    
    /**
     * Step.
     * 
     * @param       adapter
     * @return 
     */
    @Override
    public BuildServerConfig useAdapter(NetworkAdapter adapter) 
    {
        this.adapter = adapter;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       config 
     * @return 
     */
    @Override
    public BuildProtocolSchemaFile useServerConfig(ServerConfig config) 
    {
        this.serverConfig = config;
        return this;
    }
    
    
    /**
     * Step.
     * 
     * @param       protocolSchemaFile
     * @return 
     */
    @Override
    public BuildGameAppSchemaFile useProtocolSchema(File protocolSchemaFile) 
    {
        this.protocolSchema = protocolSchemaFile;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       gameAppSchemaFile
     * @return 
     */
    @Override
    public BuildProtocols useGameAppSchema(File gameAppSchemaFile) 
    {
        this.gameAppSchema = gameAppSchemaFile;
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       protcolsPath
     * @return
     * @throws      Exception  
     */
    @Override
    public BuildGameApps setupProtocolsFromPath(File protcolsPath) 
            throws Exception
    {
        this.protocols = new ConcurrentHashMap<>();
        
        // get the protocol-paths
        File[] gameAppsSubDirs = protcolsPath.listFiles();
        
        // failcheck
        if (gameAppsSubDirs == null) { throw new Exception("No protocols could be found in specified folder!"); }
        
        
        for (File path : gameAppsSubDirs) 
        {
            // check all protocol paths for sub-dirs
            // they can have names chosen by the creator of the 
            // protocols, so we only check if they are directories
            // and skip them if they are files
            if (!path.isDirectory()) { continue; }

            // we've got a possible game-app dir here, lets search for the
            // PROT-INF folder within it
            // (its a mandatory sub folder of the folder with the custom naming)
            for (File protInfPath : path.listFiles()) 
            {
                
                // skip this entry if it is no directory
                // or if the name doesnt fit our schema
                if (!protInfPath.isDirectory() ||
                    !(protInfPath.getName().endsWith("PROT-INF") ||
                      protInfPath.getName().endsWith("PROT-INF/") ||
                      protInfPath.getName().endsWith("PROT-INF\\")))
                {
                    continue;
                }
                
                // we need to create a class loader for later use with the
                // protocol (we need it to actually create the protocol filter)
                ClassLoader loader = ClassLoaderFactory.produceProtocolClassLoader(protInfPath, ClassLoader.getSystemClassLoader());
                
                // we've found the protocol folder
                // lets process it for the
                // protocol.xml
                for (File descriptorPath : protInfPath.listFiles()) 
                {
                    
                    // skip the entry if it is no file
                    // or if the name doesnt fit our schema
                    if (!descriptorPath.isFile() ||
                        !descriptorPath.getName().endsWith("protocol.xml"))
                    {
                        continue;
                    }
                    
                    // ok, we've found or deployment descriptor for this protocol now
                    // so lets try to use it for loading or filters
                    try 
                    {
                        // we've found a file that may fit our deployment descriptor schema
                        // lets try that
                        Protocol protConfig = JaxbUtils.validateAndUnmarshal(Protocol.class, descriptorPath, protocolSchema);

                        // we've got a list of filters that do something with
                        // incoming packets, and we've got a list of those that
                        // handle outgoing packets
                        List<ProtocolFilter> inFilters = new ArrayList<>();
                        List<ProtocolFilter> outFilters = new ArrayList<>();

                        // each protocol may consist of multiple protocol filters
                        for (ProtocolFilterConfig jaxbProtFiltConf : protConfig.getProtocolFilter())
                        {
                            // to create a new protocol filter, we need it's config
                            // and it's class

                            // create a new generic config out of the info
                            // found in the deployment descriptor
                            ConfigProtocolFilter filterConf = ConfigFactory.produceConfigProtocolFilter(jaxbProtFiltConf.getName(), jaxbProtFiltConf.getInitParam());

                            // try constructing the filter class
                            @SuppressWarnings("unchecked")
                            Class<ProtocolFilter> clazz = (Class<ProtocolFilter>) loader.loadClass(jaxbProtFiltConf.getClazz());

                            // create the filter!
                            ProtocolFilter newInst = clazz.newInstance();

                            // init it
                            newInst.init(filterConf);

                            // and add it to our lists, depending on its config
                            if (jaxbProtFiltConf.isIn()) { inFilters.add(newInst); }
                            // NOTE! THE OUTGOING FILTERS NEED TO BE PROCESSED IN 
                            // REVERSE ORDER, SO ALWAYS ADD OUT FILTERS TO THE FRONT
                            if (jaxbProtFiltConf.isOut()) { outFilters.add(0, newInst); }

                        }

                        // we should have created a nice ProtocolFilter list for in and outgoing filters
                        // so lets finally create the protocolchain.
                        // note that we have a mapping for ProtocolName -> ProtocolChain
                        this.protocols.put(protConfig.getName(), new ProtocolChain(inFilters, outFilters));
                        
                        // CAUTION! DONT FORGET TO REGISTER THE PROTOCOLS WITH THE NETWORK ADAPTER:
                        adapter.tryCreateProtocolListener(protConfig.getName(), (int)protConfig.getPort());
                        
                        // Also, we might want to log our findings
                        LOGGER.debug("Found a new protocol [name: " + protConfig.getName() + " | port: " + protConfig.getPort() + "]");
                    } 
                    catch (JAXBException | SAXException ex) 
                    {
                        throw new Exception("Unable to parse deployment descriptor of a protocol.", ex);
                    }
                    catch (IOException ex)
                    {
                        throw new Exception("Unable to register a protocol-listener.", ex);
                    }
                }
            }
        }
        
        // we'r done, return the updated object
        return this;
    }

    
    /**
     * Step.
     * 
     * @param       gameAppsPath
     * @return
     * @throws      Exception 
     */
    @Override
    public BuildGameAppStartup setupGameAppsFromPath(File gameAppsPath)
            throws Exception
    {
        // get all sub folders of the game app path
        // (these are the game app folders)        
        
        List<File> gameAppPaths = new ArrayList<>();
        

        File[] gameAppsSubDirs = gameAppsPath.listFiles();
        
        // failcheck
        if (gameAppsSubDirs == null) { throw new Exception("No game apps could be found in specified folder!"); }
        
        // process every sub dir of the game-apps folder
        // we expect every sub folder to be a single game app
        for (File path : gameAppsSubDirs) 
        {
            // skip if the entry is no dir at all
            if (!path.isDirectory()) { continue; }
            
            // we've got a possible game-app dir here, lets search for the
            // GAME-INF folder
            // that folder is mandatory for every game app, so the app would be
            // invalif if it wasnt there
            for (File gameInfPath : path.listFiles()) 
            {
                // skip the entry if it is no dir or if the name isnt right
                if (!gameInfPath.isDirectory() ||
                    !(gameInfPath.getName().endsWith("GAME-INF") ||
                      gameInfPath.getName().endsWith("GAME-INF/") ||
                      gameInfPath.getName().endsWith("GAME-INF\\")))
                {
                    continue;
                }
                
                // we've found the GAME-INF folder of this current app
                // lets process it in order to find it's internals,
                // especially the deployment descriptor
                for (File descriptorPath : gameInfPath.listFiles()) 
                {
                    if (!descriptorPath.isFile() || 
                        !descriptorPath.getName().endsWith("game.xml"))
                    {
                        continue;
                    }

                    try 
                    {
                        // we've found a file that may fit our delpoyment descriptor schema
                        // lets try that
                        GameApp gameConfig = JaxbUtils.validateAndUnmarshal(GameApp.class, descriptorPath, gameAppSchema);

                        // ok we could parse that DD, so
                        // lets add this game app to our list
                        gameAppsInfo.add(new ContextManager.GameAppInfo(gameConfig.getAppInfo().getDisplayName(), gameInfPath, gameConfig));
                        
                        // Also, we might want to log our findings
                        LOGGER.debug("Found a new game app [name: " + gameConfig.getAppInfo().getDisplayName() + "]");
                    } 
                    catch (JAXBException | SAXException ex) 
                    {
                        throw new Exception("Unable to parse deployment descriptor of a game app.", ex);
                    }
                }
            }
        }
        
        // we'r done, return the updated object
        return this;
    }

    
    /**
     * Step.
     * 
     * @return
     * @throws      Exception  
     */
    @Override
    public Build startupGameApps()
            throws Exception
    {
        // ok, we've added all game app infos now
        // its time to start those game apps that contain the
        // startup option
        for (GameAppInfo gameAppInfo : gameAppsInfo) 
        {
            if (gameAppInfo.getConfig().getAppInfo().getStartup() != Start.WHEN_CONTAINER_STARTUP_FINISHED)
            {
                continue;
            }
            
            // we've inherited a nice method for starting game apps, so lets use that instead of 
            // creating redundant stuff here.
            createNewGameApp(gameAppInfo.getName(), new HashMap<String, String>(), null);
        }
        
        // we'r done, return the updated object
        return this;
    }

    
    /**
     * Step. (finish this building process)
     * 
     * @return 
     */
    @Override
    public ContextManager build() 
    {
        // TODO: check if we need to clean up anything after the build process
        // here (Thats the reason this method exists)
        return this;
    }

}
