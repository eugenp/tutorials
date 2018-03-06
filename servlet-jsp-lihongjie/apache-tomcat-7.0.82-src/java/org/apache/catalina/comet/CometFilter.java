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


package org.apache.catalina.comet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletException;

/**
 * A Comet filter, similar to regular filters, performs filtering tasks on either
 * the request to a resource (a Comet servlet), or on the response from a resource, or both.
 * <br><br>
 * Filters perform filtering in the <code>doFilterEvent</code> method. Every Filter has access to
 * a FilterConfig object from which it can obtain its initialization parameters, a
 * reference to the ServletContext which it can use, for example, to load resources
 * needed for filtering tasks.
 * <p>
 * Filters are configured in the deployment descriptor of a web application
 * <p>
 * Examples that have been identified for this design are<br>
 * 1) Authentication Filters <br>
 * 2) Logging and Auditing Filters <br>
 * 3) Image conversion Filters <br>
 * 4) Data compression Filters <br>
 * 5) Encryption Filters <br>
 * 6) Tokenizing Filters <br>
 * 7) Filters that trigger resource access events <br>
 * 8) XSL/T filters <br>
 * 9) Mime-type chain Filter <br>
 * <br>
 *
 * @author Remy Maucherat
 * @author Filip Hanik
 */
public interface CometFilter extends Filter {


    /**
     * The <code>doFilterEvent</code> method of the CometFilter is called by the container
     * each time a request/response pair is passed through the chain due
     * to a client event for a resource at the end of the chain. The CometFilterChain passed in to this
     * method allows the Filter to pass on the event to the next entity in the
     * chain.<p>
     * A typical implementation of this method would follow the following pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object contained in the event with a custom implementation to
     * filter content or headers for input filtering and pass a CometEvent instance containing
     * the wrapped request to the next filter<br>
     * 3. Optionally wrap the response object contained in the event with a custom implementation to
     * filter content or headers for output filtering and pass a CometEvent instance containing
     * the wrapped request to the next filter<br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using the CometFilterChain object (<code>chain.doFilterEvent()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the next entity in the filter chain to block the event processing<br>
     * 5. Directly set fields on the response after invocation of the next entity in the filter chain.
     *
     * @param event the event that is being processed. Another event may be passed along the chain.
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilterEvent(CometEvent event, CometFilterChain chain)
        throws IOException, ServletException;


}
