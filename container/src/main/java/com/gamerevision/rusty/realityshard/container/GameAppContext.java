/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.schemas.*;
import com.gamerevision.rusty.realityshard.shardlet.*;
import com.gamerevision.rusty.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of a ShardletContext.
 * This is a basic implementation of an event-driven shardlet context,
 * that encapsulates a GameApp.
 * 
 * TODO: Review; Cleanup; Enhance the code in style and performance
 * 
 * @author _rusty
 */
public class GameAppContext implements ShardletContext
{

    private final static Logger LOGGER = LoggerFactory.getLogger(GameAppContext.class);
    
    private final EventAggregator localAggregator;
    private final ServerConfig serverConfig;
    private final GameApp appConfig;
    
    private final Pacemaker pacemaker;
    private final Map<String, String> initParams;
    private final Map<String, Object> attributes;
    
    private final Map<String, ProtocolChain> protocols;
    private final List<Shardlet> shardlets;
    
    
    /**
     * Constructor.
     * 
     * @param       executor                The executor thread pool manager, used for
     *                                      the local event-aggregator
     * @param       gameAppLoader           The classloader used for this plugin
     * @param       serverConfig            The server config object
     * @param       appConfig               The deployment descriptor
     * @param       additionalInitParams    Any additional init parameters
     * @throws      ClassNotFoundException  If the filter or shardlet has not been found
     * @throws      InstantiationException  If the filter or shardlet could not be created
     * @throws      ShardletException       If the filter or shardlet could not be initalized
     * @throws      IllegalAccessException  If the filter or shardlet could not be created
     */
    public GameAppContext(
            ScheduledExecutorService executor, 
            ClassLoader gameAppLoader, 
            ServerConfig serverConfig, 
            GameApp appConfig, 
            Map<String, String> additionalInitParams) 
            throws 
            ClassNotFoundException, 
            InstantiationException, 
            ShardletException, 
            IllegalAccessException
    {
        localAggregator = new ConcurrentEventAggregator(executor);
        this.serverConfig = serverConfig;
        this.appConfig = appConfig;
        
        
        // the current schema looks like this:
        //        <xsd:element name="DisplayName" type="xsd:string"       minOccurs="1" maxOccurs="1" />
        //        <xsd:element name="Description" type="xsd:string"       minOccurs="1" maxOccurs="1" />
        //        <xsd:element name="Startup"     type="Start"            minOccurs="0" maxOccurs="1" />
        //        <xsd:element name="HeartBeat"   type="xsd:unsignedInt"  minOccurs="0" maxOccurs="1" />
        //        <xsd:element name="InitParam"   type="InitParam"        minOccurs="0" maxOccurs="unbounded"/>
        //        <xsd:element name="Protocol"    type="Protocol"         minOccurs="1" maxOccurs="unbounded" />
        //        <xsd:element name="Shardlet"    type="Shardlet"         minOccurs="1" maxOccurs="unbounded" />
        // we can read that directly from the appConfig, so lets create
        // the game app context out of the given stuff!
        
        // DisplayName and Description do not need to be processed atm
        // they can be accessed by public methods below
        
        // Startup is important for the Container but not for us
        
        // so lets handle the HeartBeat option first
        // The only thing we do is actually just creating a new
        // Pacemaker that handles the HeartBeat later on
        // WHY THE HELL DO I FUCKING NEED TO TEMPORARILY SAVE THAT SHIT,
        // JUST TO DO A SIMPLE CAST TO A SMALLER TYPE?!
        long tmp = appConfig.getHeartBeat();
        pacemaker = new Pacemaker(executor, localAggregator, (int) tmp);
        
        // try to add the init parameters next
        // any additional init parameters that are also defined within
        // the deployment descriptor will be replaced by the latter ones
        initParams = new HashMap<>();
        initParams.putAll(additionalInitParams);
        
        for (InitParam jaxbContxInitParam : appConfig.getInitParam()) 
        {
            initParams.put(jaxbContxInitParam.getName(), jaxbContxInitParam.getValue());
        }
        
        // create the protocol chains next
        // to do so, we need to create the appropriate classes
        protocols = new ConcurrentHashMap<>();
        
        for (Protocol jaxbProtConf : appConfig.getProtocol())
        {
            // we've got a list of filters that do something with
            // incoming packets, and we've got a list of those that
            // handle outgoing packets
            List<ProtocolFilter> inFilters = new ArrayList<>();
            List<ProtocolFilter> outFilters = new ArrayList<>();
            
            // each protocol may consist of multiple protocol filters
            for (ProtocolFilterConfig jaxbProtFiltConf : jaxbProtConf.getProtocolFilter())
            {
                // to create a new protocol filter, we need it's config
                // and it's class
                
                // create a new generic config out of the info
                // found in the deployment descriptor
                Config filterConf = new GenericConfig(jaxbProtFiltConf.getName(), this, jaxbProtFiltConf.getInitParam());
                
                @SuppressWarnings("unchecked")
                Class<ProtocolFilter> clazz = (Class<ProtocolFilter>) gameAppLoader.loadClass(jaxbProtFiltConf.getClazz());

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
            protocols.put(jaxbProtConf.getName(), new ProtocolChain(inFilters, outFilters));
        }
        
        // Now, the last thing that's missing is the Shardlets of course
        // we can do that like we did with the protocol filters
        // here's what we'll need:
        // - get the shardlet list
        // - loop trough and create a generic config for it
        // - get the shardlet class
        // - create the shardlet and call init(config)
        // - add it to the aggregator and to our internal shardlet list
        shardlets = new ArrayList<>();
        
        for (ShardletConfig jaxbShardConf : appConfig.getShardlet())
        {
            
            // to create a new shardlet, we need it's config
            // and it's class

            // create a new generic config out of the info
            // found in the deployment descriptor
            Config shardletConf = new GenericConfig(jaxbShardConf.getName(), this, jaxbShardConf.getInitParam());

            @SuppressWarnings("unchecked")
            Class<Shardlet> clazz = (Class<Shardlet>) gameAppLoader.loadClass(jaxbShardConf.getClazz());

            // create the shardlet
            Shardlet newInst = clazz.newInstance();

            // init it
            newInst.init(shardletConf);

            // add it to our list
            shardlets.add(newInst);

            // add it to the aggregator
            localAggregator.addListener(newInst);

        }
        
        // create the attributes map
        attributes = new ConcurrentHashMap<>();
        
        // start the pacemaker
        pacemaker.start();
    }
    
    
    /**
     * Getter.
     * 
     * @return      The server name and version.
     */
    @Override
    public String getServerInfo() 
    {
        return "Server: [" + serverConfig.getServerName() + "] Version: [" + serverConfig.getVersion() + "]";
    }
    
    
    /**
     * Getter.
     * 
     * @return      The name of this game app, as declared in the 
     *              deployment descriptor
     */
    @Override
    public String getShardletContextName() 
    {
        return appConfig.getDisplayName();
    }

    
    /**
     * Getter.
     * 
     * @return      The local aggregator (its scope is this context)
     */
    @Override
    public EventAggregator getAggregator() 
    {
        return localAggregator;
    }

    
    /**
     * Getter.
     * Not supported yet.
     * 
     * @param       path                    The relative path to the ressource
     * @return      The ressource as input stream
     */
    @Override
    public InputStream getResourceAsStream(String path) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * Getter.
     * 
     * @param       name
     * @return      The value of the init parameter if found
     */
    @Override
    public String getInitParameter(String name) 
    {
        return initParams.get(name);
    }

    
    /**
     * Getter.
     * 
     * @return      A list of all possible parameter names 
     */
    @Override
    public Enumeration<String> getInitParameterNames() 
    {
        return Collections.enumeration(initParams.keySet());
    }

    
    /**
     * Getter.
     * 
     * @param       name
     * @return      The value of the attribute if found
     */
    @Override
    public Object getAttribute(String name) 
    {
        return attributes.get(name);
    }

    
    /**
     * Getter.
     * 
     * @return      A list of all possible attribute names
     */
    @Override
    public Enumeration<String> getAttributeNames() 
    {
        return Collections.enumeration(attributes.keySet());
    }

    
    /**
     * Setter.
     * 
     * @param       name                    The name of the new attribute
     * @param       object                  The value of the attribute
     */
    @Override
    public void setAttribute(String name, Object object) 
    {
        attributes.put(name, object);
    }
    
    
    /**
     * Deletes an attribute from the attribute collection, if it exists
     * 
     * @param       name                    Name of the attribute that will be deleted.
     */
    @Override
    public void removeAttribute(String name) 
    {
        attributes.remove(name);
    }

}
