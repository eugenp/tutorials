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
package org.apache.catalina.startup;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A test servlet that will always encode the url in case the client requires
 * session persistence but is not configured to support cookies.
 */
public class TesterServletEncodeUrl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Almost minimal processing for a servlet.
     *
     * @param nextUrl The url the caller would like to go to next. If
     *                supplied, put an encoded url into the returned
     *                html page as a hyperlink.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        out.print("OK");

        String param = req.getParameter("nextUrl");
        if (param!=null) {
            // append an encoded url to carry the sessionids
            String targetUrl = resp.encodeURL(param);
            out.print(". You want to go <a href=\"");
            out.print(targetUrl);
            out.print("\">here next</a>.");
        }
    }
}
