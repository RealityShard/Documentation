/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;

import java.io.InputStream;
import java.util.Enumeration;


/**
 * Defines a set of methods that a shardlet uses to communicate with its
 * shardlet context.
 *
 * There is one context per game app  
 *
 * @author     _rusty
 */
public interface ShardletContext 
{
    
    /**
     * Used by shardlets to send an action (packet) to the client.
     * Note: The packet needs to be in its abstract state, meaning it
     * will be compiled and packed later on within the ProtocolFilterChain
     * 
     * @param       action 
     */
    public void sendAction(ShardletAction action);
    

    /**
     * Returns the name and version of the Shardlet container on which
     * the Shardlet is running.
     *
     * @return         A <code>String</code> containing at least the 
     *                Shardlet container name and version number
     */
    public String getServerInfo();
    
    
    /**
     * Returns the name of this web application corresponding to this
     * ShardletContext as specified in the deployment descriptor for this
     * web application by the display-name element.
     *
     * @return         The name of the web application or null if no name has been
     *                 declared in the deployment descriptor.
     */
    public String getShardletContextName();
    
    
    /**
     * Getter.
     * 
     * The event-aggregator is the place where every
     * shardlet event is distributed.
     * 
     * @return      The event aggregator bound to this context. 
     */
    public EventAggregator getAggregator();
    
    
    /**
     * Returns the resource located at the named path as
     * an <code>InputStream</code> object. 
     *
     * @param       path                    The resource path
     * @return      The <code>InputStream</code> returned to the 
     *              Shardlet, or <code>null</code> if no resource
     *              exists at the specified path 
     */
    public InputStream getResourceAsStream(String path);
    
    
    /**
     * Adds a new decider to the list. If we have a new client,
     * the context will run through the deciders checking if one of them
     * accepts the client.
     * 
     * If that decider accpeted the client, the context will check whether it is
     * persistant and delete it if not.
     * 
     * A hint on the isPersistant boolean:
     * It determines if the verifier will be deleted after it has
     * accepted the first client or not. 
     * If this is persistant,there will be no way of deleting it, 
     * except by calling the <code>clearClientVerifiers</code> 
     * method, that deletes every verifier of the list
     * 
     * @param       verifier                Checks whether we want to accept a new
     *                                      client based on its first message
     * @param       isPersistant            See above description. Should only be true
     *                                      if you want to auto-accept new clients.
     */
    public void addClientVerifier(ShardletActionVerifier verifier, boolean isPersistant);
    
    
    /**
     * Clears the client verifiers list.
     * 
     * If the paramter is true, this will ignore any verifier that is not
     * persistant and instead remove all persistant ones.
     * (This is especially helpful when you want to autoaccept clients
     *  only temporary, but dont want to loose any special non-persistant clients
     *  when you decide to end the auto accept period)
     * 
     * @param persistantVerifiersOnly       Determines whether the context should
     *                                      delete only persistant verifiers.
     */
    public void clearClientVerifiers(boolean persistantVerifiersOnly);
    
    
    /**
     * Getter.
     * 
     * @param       name                    The name of the (generic) parameter.
     *                                      The object will try to find it based on that string.
     * @return      The parameter's value if found
     */
    public String getInitParameter(String name);


    /**
     * Getter.
     * 
     * @return      All parameter names (the keys without values)
     */
    public Enumeration<String> getInitParameterNames();


    /**
     * Getter.
     * 
     * Returns the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound under the name.
     *
     * @param       name                    A string specifying the name of the object
     * @return                              The object with the specified name
     */
    public Object getAttribute(String name);
            

    /**
     * Getter.
     * 
     * Returns an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of all the objects bound to this session. 
     *
     * @return		The names of all currently available attributes
     */    
    public Enumeration<String> getAttributeNames();
    

    /**
     * Binds an object to this session, using the name specified.
     * If an object of the same name is already bound to the session,
     * the object is replaced.
     *
     * <p>If the value passed in is null, this has the same effect as calling 
     * <code>removeAttribute()<code>.
     *
     *
     * @param       name                    The name to which the object is bound;
     *					    cannot be null
     * @param       value                   The object to be bound
     */
    public void setAttribute(String name, Object value);


    /**
     * Removes the object bound with the specified name from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * @param       name                    The name of the object to
     *                                      remove from this session
     */
    public void removeAttribute(String name);

}


