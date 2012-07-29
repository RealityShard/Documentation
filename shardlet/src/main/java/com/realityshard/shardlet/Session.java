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
    
    public enum EncryptionStates 
    {
        /**
         * Meaning the action is only available as network-packet-bytes
         */
        UNENCRYPTED,
        
        /**
         * Meaning the protocol filters should encrypt packets from this session
         */
        ENCRYPTED;
        
        private Object attachment;

        
        /**
         * Stick your data for packet/stream encryption to this
         * State object.
         * 
         * @return      Your data.
         */
        public Object getAttachment() 
        {
            return attachment;
        }

        
        /**
         * Stick your data for packet/stream encryption to this
         * State object.
         * 
         * @param       attachment              Your data. 
         */
        public void setAttachment(Object attachment) 
        {
            this.attachment = attachment;
        }
    }
    
    
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
    public void setEncryptionState(EncryptionStates state);
    
    
    /**
     * Getter.
     * Get the current encryption state of this session.
     * 
     * @return       The current encryption state. 
     */
    public EncryptionStates getEncryptionState();
    
        
    /**
     * Getter.
     * Returns your session-specific data that you attached to this session,
     * or null if there was nothing attached.
     * 
     * @return      The session-specific data.
     */
    public Object getAttachment();


    /**
     * Setter.
     * This adds any session specific data to this session.
     * 
     * Beware of casting exceptions! You may want to have a single class
     * that manages all session specific data and only use that one as
     * an attachment
     * 
     * @param       attachment              The session-specific data. 
     */
    public void setAttachment(Object attachment);

}

