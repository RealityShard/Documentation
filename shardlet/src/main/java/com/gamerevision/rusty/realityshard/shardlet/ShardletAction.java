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

import java.nio.ByteBuffer;
import java.util.Enumeration;


/**
 * Defines an object to provide a message to or from a client, by:
 * 
 * A) The Shardlet container creates a <code>ShardletAction</code> object and passes
 * it as an argument to the Shardlet's handler method.
 *
 * B) The Shardlet creates a <code>ShardletAction</code> object and passes
 * it as an argument to the Shardlet container where it is distributed.
 * 
 * Partly taken from javax.servlet.ServletRequest / ServletResponse
 * Thx Oracle!
 * 
 * @author _rusty
 */
public interface ShardletAction
{
    /**
     * Returns the value of the named attribute as an <code>Object</code>,
     * or <code>null</code> if no attribute of the given name exists. 
     *
     * @param       name                    A <code>String</code> specifying the name 
     *                                      of the attribute
     * @return      The value-object or null.
     */
    public Object getAttribute(String name);
    
    
    /**
     * Stores an attribute in this action.
     * Attributes are reset between actions.
     *
     * @param       name                    A <code>String</code> specifying 
     *                                      the name of the attribute
     * @param       o                       The <code>Object</code> to be stored
     */
    public void setAttribute(String name, Object o);
    

    /**
     * Returns an <code>Enumeration</code> containing the
     * names of the attributes available to this action.
     *
     * @return      An <code>Enumeration</code> of attribute 
     *              names
     */
    public Enumeration<String> getAttributeNames();
    
    
    /**
     *
     * Removes an attribute from this action.  This method is not
     * generally needed as attributes only persist as long as the action
     * is being handled.
     *
     * @param       name                    A <code>String</code> specifying 
     *                                      the name of the attribute to remove
     */
    public void removeAttribute(String name);
    
    
    /**
     * Getter.
     * 
     * @return      The underlying NIO buffer.
     */
    public ByteBuffer getBuffer();
    
    
    /**
     * Setter.
     * 
     * @param       buf                     The <code>ByteBuffer</code> object that will be
     *                                      set as the new Buffer
     */
    public void setBuffer(ByteBuffer buf);
    
    
    /**
     * Returns the name and version of the protocol the action uses.
     * The protocol is a string taken from the deployment descriptor.
     *
     * @return      A <code>String</code> containing the protocol name and version number
     */    
    public String getProtocol();
    
    
    /**
     * Getter.
     * 
     * @return      The network session object.
     */
    public Session getSession();
    
    
    /**
     * Returns a boolean indicating if the response has been
     * committed.  A committed response cannot be changed.
     *
     * @return      A boolean indicating if the response has been
     *              committed
     */
    public boolean isCommitted();

}
