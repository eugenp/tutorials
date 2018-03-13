/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.catalina;


import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;


/**
 * <p>A <b>Valve</b> is a request processing component associated with a
 * particular Container.  A series of Valves are generally associated with
 * each other into a Pipeline.  The detailed contract for a Valve is included
 * in the description of the <code>invoke()</code> method below.</p>
 *
 * <b>HISTORICAL NOTE</b>:  The "Valve" name was assigned to this concept
 * because a valve is what you use in a real world pipeline to control and/or
 * modify flows through it.
 *
 * @author Craig R. McClanahan
 * @author Gunnar Rjnning
 * @author Peter Donald
 */
public interface Valve {


    //-------------------------------------------------------------- Properties


    /**
     * Return descriptive information about this Valve implementation.
     */
    public String getInfo();


    /**
     * Return the next Valve in the pipeline containing this Valve, if any.
     */
    public Valve getNext();


    /**
     * Set the next Valve in the pipeline containing this Valve.
     *
     * @param valve The new next valve, or <code>null</code> if none
     */
    public void setNext(Valve valve);


    //---------------------------------------------------------- Public Methods


    /**
     * Execute a periodic task, such as reloading, etc. This method will be
     * invoked inside the classloading context of this container. Unexpected
     * throwables will be caught and logged.
     */
    public void backgroundProcess();


    /**
     * <p>Perform request processing as required by this Valve.</p>
     *
     * <p>An individual Valve <b>MAY</b> perform the following actions, in
     * the specified order:</p>
     * <ul>
     * <li>Examine and/or modify the properties of the specified Request and
     *     Response.
     * <li>Examine the properties of the specified Request, completely generate
     *     the corresponding Response, and return control to the caller.
     * <li>Examine the properties of the specified Request and Response, wrap
     *     either or both of these objects to supplement their functionality,
     *     and pass them on.
     * <li>If the corresponding Response was not generated (and control was not
     *     returned, call the next Valve in the pipeline (if there is one) by
     *     executing <code>getNext().invoke()</code>.
     * <li>Examine, but not modify, the properties of the resulting Response
     *     (which was created by a subsequently invoked Valve or Container).
     * </ul>
     *
     * <p>A Valve <b>MUST NOT</b> do any of the following things:</p>
     * <ul>
     * <li>Change request properties that have already been used to direct
     *     the flow of processing control for this request (for instance,
     *     trying to change the virtual host to which a Request should be
     *     sent from a pipeline attached to a Host or Context in the
     *     standard implementation).
     * <li>Create a completed Response <strong>AND</strong> pass this
     *     Request and Response on to the next Valve in the pipeline.
     * <li>Consume bytes from the input stream associated with the Request,
     *     unless it is completely generating the response, or wrapping the
     *     request before passing it on.
     * <li>Modify the HTTP headers included with the Response after the
     *     <code>getNext().invoke()</code> method has returned.
     * <li>Perform any actions on the output stream associated with the
     *     specified Response after the <code>getNext().invoke()</code> method has
     *     returned.
     * </ul>
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     *
     * @exception IOException if an input/output error occurs, or is thrown
     *  by a subsequently invoked Valve, Filter, or Servlet
     * @exception ServletException if a servlet error occurs, or is thrown
     *  by a subsequently invoked Valve, Filter, or Servlet
     */
    public void invoke(Request request, Response response)
        throws IOException, ServletException;

    
    /**
     * Process a Comet event.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     *
     * @exception IOException if an input/output error occurs, or is thrown
     *  by a subsequently invoked Valve, Filter, or Servlet
     * @exception ServletException if a servlet error occurs, or is thrown
     *  by a subsequently invoked Valve, Filter, or Servlet
     */
    public void event(Request request, Response response, CometEvent event)
        throws IOException, ServletException;

    
    public boolean isAsyncSupported();
    

}
