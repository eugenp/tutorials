/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.coyote;

import org.apache.tomcat.util.net.SocketStatus;

/**
 * Adapter. This represents the entry point in a coyote-based servlet container.
 *
 *
 * @author Remy Maucherat
 * @see ProtocolHandler
 */
public interface Adapter {

    /** 
     * Call the service method, and notify all listeners
     *
     * @exception Exception if an error happens during handling of
     *   the request. Common errors are:
     *   <ul><li>IOException if an input/output error occurs and we are
     *   processing an included servlet (otherwise it is swallowed and
     *   handled by the top level error handler mechanism)
     *       <li>ServletException if a servlet throws an exception and
     *  we are processing an included servlet (otherwise it is swallowed
     *  and handled by the top level error handler mechanism)
     *  </ul>
     *  Tomcat should be able to handle and log any other exception ( including
     *  runtime exceptions )
     */
    public void service(Request req, Response res)
            throws Exception;

    public boolean event(Request req, Response res, SocketStatus status)
            throws Exception;
    
    public boolean asyncDispatch(Request req,Response res, SocketStatus status)
            throws Exception;

    public void errorDispatch(Request request, Response response);

    public void log(Request req, Response res, long time);

    /**
     * Assert that request and response have been recycled. If they have not
     * then log a warning and force a recycle. This method is called as a safety
     * check when a processor is being recycled and may be returned to a pool
     * for reuse.
     *
     * @param req
     *            Request
     * @param res
     *            Response
     */
    public void checkRecycled(Request req, Response res);

    /**
     * Provide the name of the domain to use to register MBeans for components
     * associated with the connector.
     * 
     * @return  The MBean domain name
     */
    public String getDomain();
}
