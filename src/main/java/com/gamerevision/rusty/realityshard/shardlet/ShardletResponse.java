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
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Defines an object to assist a servlet in sending a response to the client.
 * The servlet container creates a <code>ServletResponse</code> object and
 * passes it as an argument to the servlet's <code>service</code> method.
 * 
 * Thx Oracle!
 *
 * @author _rusty
 */
 
public interface ShardletResponse 
{

    /**
     * Returns a {@link OutputStream} suitable for writing binary 
     * data in the response. The servlet container does not encode the
     * binary data.  
     *
     * <p> Calling flush() on the ServletOutputStream commits the response.
     *
     * Either this method or {@link #getWriter} may 
     * be called to write the body, not both, except when {@link #reset}
     * has been called.
     *
     * @return 		A {@link OutputStream} for writing binary data 
     * @exception 	IOException 			If an input or output exception occurred
     */
    public OutputStream getOutputStream() 
    		throws IOException;
    
    /**
     * Returns a <code>PrintWriter</code> object that
     * can send character text to the client.
     * 
     * @return 		A <code>PrintWriter</code> object that 
     * 				can return character data to the client 
     * @exception 	IOException 			If an input or output exception occurred
     */
    public PrintWriter getWriter() 
    		throws IOException;
    

    /**
     * Sets the preferred buffer size for the body of the response.  
     * The servlet container will use a buffer at least as large as 
     * the size requested.  The actual buffer size used can be found
     * using <code>getBufferSize</code>.
     * 
     * <p>This method must be called before any response body content is
     * written; if content has been written or the response object has
     * been committed, this method throws an 
     * <code>IllegalStateException</code>.
     *
     * @param 		size 					The preferred buffer size
     */
    public void setBufferSize(int size);
   
    
    /**
     * Returns the actual buffer size used for the response.  If no buffering
     * is used, this method returns 0.
     *
     * @return 		The actual buffer size used
     */
    public int getBufferSize();
    
    
    /**
     * Forces any content in the buffer to be written to the client.  A call
     * to this method automatically commits the response, meaning the status 
     * code and headers will be written.
     * 
     * @throws      IOException             The usual crap.
     */
    public void flushBuffer() 
    		throws IOException;
    
    
    /**
     * Clears the content of the underlying buffer. If the 
     * response has been committed, this method throws an 
     * <code>IllegalStateException</code>.
     */
    public void resetBuffer();
    
    
    /**
     * Returns a boolean indicating if the response has been
     * committed.  A committed response has already had its status 
     * code and headers written.
     *
     * @return      A boolean indicating if the response has been
     *              committed
     */
    public boolean isCommitted();
}





