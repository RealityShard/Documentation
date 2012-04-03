/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2011 Oracle and/or its affiliates. All rights reserved.
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

import java.io.InputStream;
import java.util.Enumeration;


/**
 * This file was edited by _rusty: no need for JSP
 */

/**
 * Defines a set of methods that a shardlet uses to communicate with its
 * shardlet container, for example, dispatch requests, or write to a log file.
 *
 * There is one context per game app per JVM.  
 * 
 * Thx Oracle!
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
     * Returns a <code>String</code> containing the value of the named
     * context-wide initialization parameter, or <code>null</code> if the 
     * parameter does not exist.
     * 
     * @param       name                    The parameter name.
     * @return      The parameter value, if the parameter was found.
     */
    public String getInitParameter(String name);


    /**
     * Returns the names of the context's initialization parameters as an
     * <code>Enumeration</code> of <code>String</code> objects, or an
     * empty <code>Enumeration</code> if the context has no initialization
     * parameters.
     *
     * @return      An <code>Enumeration</code> of <code>String</code> 
     *              objects containing the names of the context's
     *              initialization parameters
     */
    public Enumeration<String> getInitParameterNames();


    /**
     * Returns the Shardlet container attribute with the given name, 
     * or <code>null</code> if there is no attribute by that name.
     * Hint: To store stuff game-app wide.
     *
     * @param         name                     A <code>String</code> specifying the name 
     *                                        of the attribute
     * @return         The value-object or null.
     */
    public Object getAttribute(String name);
    

    /**
     * Returns an <code>Enumeration</code> containing the 
     * attribute names available within this ShardletContext.
     *
     * @return         An <code>Enumeration</code> of attribute 
     *                names
     */
    public Enumeration<String> getAttributeNames();
    
    
    /**
     * Binds an object to a given attribute name in this ShardletContext. If
     * the name specified is already used for an attribute, this
     * method will replace the attribute with the new to the new attribute.
     * <p>If listeners are configured on the <code>ShardletContext</code> the  
     * container notifies them accordingly.
     * <p>
     * If a null value is passed, the effect is the same as calling 
     * <code>removeAttribute()</code>.
     * 
     * <p>Attribute names should follow the same convention as package
     * names. The Java Shardlet API specification reserves names
     * matching <code>java.*</code>, <code>javax.*</code>, and
     * <code>sun.*</code>.
     *
     * @param         name                     A <code>String</code> specifying the name 
     *                                        of the attribute
     * @param         object                     An <code>Object</code> representing the
     *                                        attribute to be bound
     */
    public void setAttribute(String name, Object object);
    

    /**
     * Removes the attribute with the given name from 
     * this ShardletContext. After removal, subsequent calls to
     * {@link #getAttribute} to retrieve the attribute's value
     * will return <code>null</code>.
     *
     * <p>If listeners are configured on the <code>ShardletContext</code> the 
     * container notifies them accordingly.
     *
     * @param         name                    A <code>String</code> specifying the name 
     *                                         of the attribute to be removed
     */
    public void removeAttribute(String name);  

}


