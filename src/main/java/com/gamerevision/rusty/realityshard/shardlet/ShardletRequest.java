/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2012 Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.util.*;

/**
 * Defines an object to provide client request information to a Shardlet.  The
 * Shardlet container creates a <code>ShardletRequest</code> object and passes
 * it as an argument to the Shardlet's <code>service</code> method.
 *
 * <p>A <code>ShardletRequest</code> object provides data including
 * parameter name and values, attributes, and an input stream.
 * Interfaces that extend <code>ShardletRequest</code> can provide
 * additional protocol-specific data (for example, HTTP data is
 * provided by {@link javax.Shardlet.http.HttpShardletRequest}.
 * 
 * Thx Oracle!
 * 
 * @author _rusty
 */
public interface ShardletRequest 
{
	/**
     * Returns the value of the named attribute as an <code>Object</code>,
     * or <code>null</code> if no attribute of the given name exists. 
     *
     * @param 		name 					A <code>String</code> specifying the name 
     *										of the attribute
     * @return 		The value-object or null.
     */
    public Object getAttribute(String name);
    
    
    /**
     * Stores an attribute in this request.
     * Attributes are reset between requests.  This method is most
     * often used in conjunction with {@link RequestDispatcher}.
     *
     * @param 		name 					A <code>String</code> specifying 
     * 										the name of the attribute
     * @param 		o 						The <code>Object</code> to be stored
     */
    public void setAttribute(String name, Object o);
    

    /**
     * Returns an <code>Enumeration</code> containing the
     * names of the attributes available to this request.
     *
     * @return 		An <code>Enumeration</code> of attribute 
     *				names
     */
    public Enumeration<String> getAttributeNames();
    
    
    /**
     *
     * Removes an attribute from this request.  This method is not
     * generally needed as attributes only persist as long as the request
     * is being handled.
     *
     * @param 		name 					A <code>String</code> specifying 
     * 										the name of the attribute to remove
     */
    public void removeAttribute(String name);
  

    /**
     * Returns the length, in bytes, of the request body and made available by
     * the input stream, or -1 if the length is not known. 
     *
     * @return 		An integer containing the length of the request body or -1 if
     * 				the length is not known
     */
    public int getContentLength();
    
    
    /**
     * Retrieves the body of the request as binary data using
     * a {@link InputStream}.  Either this method or 
     * {@link #getReader} may be called to read the body, not both.
     *
     * @return 		A {@link InputStream} object containing
     * 				the body of the request
     * @exception 	IOException 			If an input or output exception occurred
     */
    public InputStream getInputStream() 
    			throws IOException; 
     
    
    /**
     * Returns the value of a request parameter as a <code>String</code>,
     * or <code>null</code> if the parameter does not exist. Request parameters
     * are extra information sent with the request.
     *
     * @param 		name 					A <code>String</code> specifying the name of the parameter
     * @return 		A <code>String</code> representing the single value of
     * 				the parameter
     */
    public String getParameter(String name);
    
    
    /**
     *
     * Returns an <code>Enumeration</code> of <code>String</code>
     * objects containing the names of the parameters contained
     * in this request. If the request has 
     * no parameters, the method returns an empty <code>Enumeration</code>. 
     *
     * @return 		An <code>Enumeration</code> of <code>String</code>
     * 				objects, each <code>String</code> containing the name of
     * 				a request parameter; or an empty <code>Enumeration</code>
     * 				if the request has no parameters
     */
    public Enumeration<String> getParameterNames();
        
    
    /**
     * Returns an array of <code>String</code> objects containing 
     * all of the values the given request parameter has, or 
     * <code>null</code> if the parameter does not exist.
     *
     * @param 		name 					A <code>String</code> containing the name of 
     * 										the parameter whose value is requested
     * @return 		An array of <code>String</code> objects 
     * 				containing the parameter's values
     */
    public String[] getParameterValues(String name);
 
    
    /**
     * Returns a java.util.Map of the parameters of this request.
     * 
     * @return 		An immutable java.util.Map containing parameter names as 
     * 				keys and parameter values as map values.
     */
    public Map<String, String[]> getParameterMap();
    
    
    /**
     * Returns the name and version of the protocol the request uses
     * in the form <i>protocol/majorVersion.minorVersion</i>
     *
     * @return 		A <code>String</code> containing the protocol 
     * 				name and version number
     */    
    public String getProtocol();
   
    
    /**
     * Returns the host name of the server to which the request was sent.
     * It is the value of the part before ":" in the <code>Host</code>
     * header value, if any, or the resolved server name, or the server IP
     * address.
     *
     * @return 		A <code>String</code> containing the name of the server
     */
    public String getServerName();
    
    
    /**
     * Returns the port number to which the request was sent.
     * It is the value of the part after ":" in the <code>Host</code>
     * header value, if any, or the server port where the client connection
     * was accepted on.
     *
     * @return 		An integer specifying the port number
     */
    public int getServerPort();
    
    
    /**
     * Retrieves the body of the request as character data using
     * a <code>BufferedReader</code>.  The reader translates the character
     * data according to the character encoding used on the body.
     * Either this method or {@link #getInputStream} may be called to read the
     * body, not both.
     * 
     * @return 		A <code>BufferedReader</code> containing the body of the request 
     * @exception 	IOException 			If an input or output exception occurred
     */
    public BufferedReader getReader() 
    		throws IOException;
    
  
    /**
     * Returns the fully qualified name of the client
     * or the last proxy that sent the request.
     * If the engine cannot or chooses not to resolve the hostname 
     * (to improve performance), this method returns the dotted-string form of 
     * the IP address.
     *
     * @return 		A <code>String</code> containing the fully 
     * 				qualified name of the client
     */
    public String getRemoteHost();
    
    
    /**
     * Returns the Internet Protocol (IP) address of the client 
     * or last proxy that sent the request.
     *
     * @return 		A <code>String</code> containing the 
     * 				IP address of the client that sent the request
     */
    public String getRemoteAddr();
    
    
    /**
     * Returns the Internet Protocol (IP) source port of the client
     * or last proxy that sent the request.
     *
     * @return 		An integer specifying the port number
     */    
    public int getRemotePort();

    
    /**
     * Returns the host name of the Internet Protocol (IP) interface on
     * which the request was received.
     *
     * @return 		A <code>String</code> containing the host
     *         		name of the IP on which the request was received.
     */
    public String getLocalName();

    
    /**
     * Returns the Internet Protocol (IP) address of the interface on
     * which the request  was received.
     *
     * @return 		A <code>String</code> containing the
     * 				IP address on which the request was received. 
     */       
    public String getLocalAddr();

    
    /**
     * Returns the Internet Protocol (IP) port number of the interface
     * on which the request was received.
     *
     * @return 		An integer specifying the port number
     */
    public int getLocalPort();
}