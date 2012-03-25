/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gamerevision.rusty.realityshard.shardlet;

import java.util.Enumeration;


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
     * @return		The unique identifier as string (usually a hex string)
     */
    public String getId();
    
    
    /**
     * Getter.
     * 
     * @return      The ShardletContext object for the game application
     */
    public ShardletContext getShardletContext();


    /**
     * Setter.
     * 
     * Specifies the time, in seconds, between client requests before the 
     * Shardlet container will invalidate this session. 
     *
     * @param       interval		        An integer specifying the number
     * 				                        of seconds 
     */    
    public void setMaxInactiveInterval(int interval);


    /**
     * Getter.
     * 
     * Returns the maximum time interval, in seconds, between 
     * client accesses before the Shardlet container
     * will invalidate the session.
     *
     * <p>A return value of zero or less indicates that the
     * session will never timeout.
     *
     * @return		An integer specifying the number of
     *			    seconds this session remains open
     *			    between client requests
     */
    public int getMaxInactiveInterval();
    

    /**
     * Getter.
     * 
     * Returns the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound under the name.
     *
     * @param       name		            A string specifying the name of the object
     * @return		The object with the specified name
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
     * @param       name			        The name to which the object is bound;
     *					                    cannot be null
     * @param       value			        The object to be bound
     */
    public void setAttribute(String name, Object value);


    /**
     * Removes the object bound with the specified name from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * @param       name				    The name of the object to
     *						                remove from this session
     */
    public void removeAttribute(String name);
    
    
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
     * Invalidates this session then unbinds any objects bound
     * to it. 
     */
    public void invalidate();

}

