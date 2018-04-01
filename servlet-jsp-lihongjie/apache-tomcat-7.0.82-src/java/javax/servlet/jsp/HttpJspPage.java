/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
 
package javax.servlet.jsp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The HttpJspPage interface describes the interaction that a JSP Page
 * Implementation Class must satisfy when using the HTTP protocol.
 *
 * <p>
 * The behaviour is identical to that of the JspPage, except for the signature
 * of the _jspService method, which is now expressible in the Java type
 * system and included explicitly in the interface.
 * 
 * @see JspPage
 */

public interface HttpJspPage extends JspPage {

    /** The _jspService()method corresponds to the body of the JSP page. This
     * method is defined automatically by the JSP container and should never
     * be defined by the JSP page author.
     * <p>
     * If a superclass is specified using the extends attribute, that
     * superclass may choose to perform some actions in its service() method
     * before or after calling the _jspService() method.  See using the extends
     * attribute in the JSP_Engine chapter of the JSP specification.
     *
     * @param request Provides client request information to the JSP.
     * @param response Assists the JSP in sending a response to the client.
     * @throws ServletException Thrown if an error occurred during the 
     *     processing of the JSP and that the container should take 
     *     appropriate action to clean up the request.
     * @throws IOException Thrown if an error occurred while writing the
     *     response for this page.
     */
    public void _jspService(HttpServletRequest request,
                            HttpServletResponse response)
       throws ServletException, IOException;
}
