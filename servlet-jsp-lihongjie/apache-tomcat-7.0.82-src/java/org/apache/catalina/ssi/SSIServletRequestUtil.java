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
package org.apache.catalina.ssi;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.RequestUtil;

public class SSIServletRequestUtil {
    /**
     * Return the relative path associated with this servlet. Taken from
     * DefaultServlet.java. Perhaps this should be put in
     * org.apache.catalina.util somewhere? Seems like it would be widely used.
     * 
     * @param request
     *            The servlet request we are processing
     */
    public static String getRelativePath(HttpServletRequest request) {
        // Are we being processed by a RequestDispatcher.include()?
        if (request.getAttribute(
                RequestDispatcher.INCLUDE_REQUEST_URI) != null) {
            String result = (String)request.getAttribute(
                    RequestDispatcher.INCLUDE_PATH_INFO);
            if (result == null)
                result = (String)request.getAttribute(
                        RequestDispatcher.INCLUDE_SERVLET_PATH);
            if ((result == null) || (result.equals(""))) result = "/";
            return (result);
        }
        // No, extract the desired path directly from the request
        String result = request.getPathInfo();
        if (result == null) {
            result = request.getServletPath();
        }
        if ((result == null) || (result.equals(""))) {
            result = "/";
        }
        return RequestUtil.normalize(result);
    }

}