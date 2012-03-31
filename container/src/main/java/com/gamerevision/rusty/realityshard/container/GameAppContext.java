/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.schemas.GameApp;
import com.gamerevision.rusty.realityshard.schemas.InitParam;
import com.gamerevision.rusty.realityshard.schemas.ServerConfig;
import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.ShardletContext;
import com.gamerevision.rusty.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;


/**
 * Implementation of a ShardletContext.
 * This is a basic implementation of an event-driven shardlet context,
 * that encapsulates a GameApp.
 * 
 * @author _rusty
 */
public class GameAppContext implements ShardletContext
{
    
    private final EventAggregator localAggregator;
    private final ServerConfig serverConfig;
    private final GameApp appConfig;
    
    private final Map<String, String> initParams;
    private final Map<String, Object> attributes;
    
    
    /**
     * Constructor.
     * 
     * @param       executor                The executor thread pool manager, used for
     *                                      the local event-aggregator
     * @param       serverConfig            The server config object
     * @param       appConfig               The deployment descriptor
     * @param       additionalInitParams    Any additional init parameters
     */
    public GameAppContext(Executor executor, ServerConfig serverConfig, GameApp appConfig, Map<String, String> additionalInitParams)
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
        // the game app context out the given stuff!
        
        // try to add the init parameters
        // any additional init parameters that are also defined within
        // the deployment descriptor will be replaced by the latter ones
        initParams = new HashMap<>();
        initParams.putAll(additionalInitParams);
        
        for (InitParam param : appConfig.getInitParam()) 
        {
            initParams.put(param.getName(), param.getValue());
        }
        
        // try to add the shardlets to the aggregator
        
        // create the attributes map
        attributes = new ConcurrentHashMap<>();
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
