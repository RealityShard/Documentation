/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.utils;

import com.realityshard.shardlet.Action;
import com.realityshard.shardlet.ConfigShardlet;
import com.realityshard.shardlet.Event;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.Shardlet;
import com.realityshard.shardlet.ShardletContext;
import java.util.Enumeration;


/**
 * This class is a generic shardlet template, that will auto-load the
 * config and household the config/context/aggregator so the sub class
 * doesn't need to implement that code over and over again.
 * Inherit each shardlet from this class for simplicity of usage.
 * 
 * @author _rusty
 */
public abstract  class GenericShardlet implements Shardlet, ConfigShardlet
{    
    private ConfigShardlet config;
    private ShardletContext context;
    private EventAggregator aggregator;
   
    
    /**
     * Constructor.
     */
    public GenericShardlet() {}
    
    
    /**
     * Called when the shardlet is going to be taken out of service.
     */
    @Override
    public void destroy() {}
    
    
    /**
     * Extracts the event-aggregator from the context.
     * 
     * When using this class, make sure to override the init() method,
     * as it will be called by this method.
     * 
     * @param       config
     */
    @Override
    public final void init(ConfigShardlet config) 
            throws Exception 
    {
        // init the attributes
        this.config = config;
        this.context = config.getShardletContext();
        this.aggregator = context.getAggregator();
        
        // call the init() method - it will be overridden by the
        // sub class.
        init();
    }
    
    
    /**
     * Will be called by the init(config) method,
     * so just override this to ensure your code
     * gets loaded at shardlet-startup.
     * This method can also be left empty.
     * 
     * @throws      Exception               If anything went wrong during 
     *                                      initialization.  
     */
    protected abstract void init()
            throws Exception;
    
    
    /**
     * Publish an Event.
     * This hides the aggregator from sub types.
     * 
     * Note that we need to make this generic, because we need to keep the type.
     * 
     * @param       <E>                     The event type.
     * @param       event                   The event object.
     */
    protected final <E extends Event> void publishEvent(E event)
    {
        aggregator.triggerEvent(event);
    }
    
    
    /**
     * Send an action to a client.
     * The client is determined by the <code>Session</code> object,
     * the action must be a valid packet that uses a known protocol.
     * 
     * @param       action                  A valid action with attached Session
     *                                      and a valid protocol string.
     */
    protected final void sendAction(Action action)
    {
        context.sendAction(action);
    }
    
    
    /**
     * Getter.
     * 
     * @return      The current config.
     */
    @Override
    public ConfigShardlet getShardletConfig() 
    {
        return config;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The current context.
     */
    @Override
    public ShardletContext getShardletContext() 
    {
        return context;
    }

    
    /**
     * Getter.
     * 
     * @return      The current info. (Empty in GenericShardlet)
     */
    @Override
    public String getShardletInfo() 
    {   
        return "";
    }

    
    /**
     * Getter.
     *
     * @return      The name of this shardlet instance
     */
    @Override
    public String getName() 
    {
        ConfigShardlet sc = getShardletConfig();
        
        if (sc == null) 
        {
            throw new IllegalStateException("Error: Shardlet config not initialized!");
        }

        return sc.getName();
    }
    


    /**
     * Getter.
     * 
     * @param       name                    The name of the parameter that we'll try to
     *                                      access
     * @return      The value if the parameter exists
     */
    @Override
    public String getInitParameter(String name) 
    {
        ConfigShardlet sc = getShardletConfig();
        
        if (sc == null) 
        {
            throw new IllegalStateException("Error: Shardlet config not initialized!");
        }

        return sc.getInitParameter(name);
    }

    
    /**
     * Getter.
     * 
     * @return      An enumeration of valid parameter-name strings.
     */
    @Override
    public Enumeration<String> getInitParameterNames() 
    {
        ConfigShardlet sc = getShardletConfig();
        
        if (sc == null) 
        {
            throw new IllegalStateException("Error: Shardlet config not initialized!");
        }

        return sc.getInitParameterNames();
    }
}
