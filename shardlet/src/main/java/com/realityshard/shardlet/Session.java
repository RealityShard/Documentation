/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;

import java.util.Enumeration;
import java.util.UUID;


/**
 * A <b>persistant</b> user-session that is unique for every connection.
 * 
 * Loosely based on javax.Shardlet.http.HttpSession
 * Thx Oracle!
 *
 * @author	_rusty
 */
public interface Session 
{
        
    /**
     * Getter.
     * 
     * @return      The unique identifier as UUID
     */
    public UUID getId();

   
    /**
     * Returns the Internet Protocol (IP) address of the client 
     * or last proxy that sent the request.
     *
     * @return      A <code>String</code> containing the 
     *              IP address of the client that sent the request
     */
    public String getRemoteAddr();
    
    
    /**
     * Returns the Internet Protocol (IP) source port of the client
     * or last proxy that sent the request.
     *
     * @return      An integer specifying the port number
     */    
    public int getRemotePort();
    
    
    /**
     * Returns the name of the protocol the session uses.
     * The protocol is a string taken from the deployment descriptor of the protocol.
     *
     * @return      A <code>String</code> containing the protocol name,
     *              as used in the deployment descriptor.
     */    
    public String getProtocol();


    /**
     * Invalidates this session then unbinds any objects bound
     * to it. 
     */
    public void invalidate();
    
    
    /**
     * Setter.
     * Set a new encryption state of this session.
     * 
     * @param       state                   The new encryption state. 
     */
    public void setEncryptionState(SessionEncryptionState state);
    
    
    /**
     * Getter.
     * Get the current encryption state of this session.
     * 
     * @return       The current encryption state. 
     */
    public SessionEncryptionState getEncryptionState();
    

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

