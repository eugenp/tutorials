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
package org.apache.catalina.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TesterTldListener implements ServletContextListener {

    private static StringBuilder log = new StringBuilder();

    public static String getLog() {
        return log.toString();
    }

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext sc = sce.getServletContext();

        // Try and use one of the Servlet 3.0 methods that should be blocked
        try {
            servletContext = sce.getServletContext();
            sc.getEffectiveMajorVersion();
            log.append("FAIL-01");
        } catch (UnsupportedOperationException uoe) {
            log.append("PASS-01");
        } catch (Exception e) {
            log.append("FAIL-02");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Bug 57446. Same ServletContext should be presented as at init
        if (servletContext == sce.getServletContext()) {
            log.append("PASS-02");
        } else {
            //log.append("FAIL-03");
        }
    }
}
