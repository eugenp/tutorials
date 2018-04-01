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

package org.apache.catalina.startup;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TesterServletWithAnnotations extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Resource(mappedName = "1")
    private int envEntry1;

    private int envEntry2;

    private int envEntry3;

    private int envEntry4;

    @Resource(name = "envEntry5", mappedName = "5")
    private int envEntry5;

    private int envEntry6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().print("envEntry1: " + envEntry1);
        resp.getWriter().print(" envEntry2: " + envEntry2);
        resp.getWriter().print(" envEntry3: " + envEntry3);
        resp.getWriter().print(" envEntry4: " + envEntry4);
        resp.getWriter().print(" envEntry5: " + envEntry5);
        resp.getWriter().print(" envEntry6: " + envEntry6);
    }

    public void setEnvEntry2(int envEntry2) {
        this.envEntry2 = envEntry2;
    }

    @Resource(mappedName = "3")
    public void setEnvEntry3(int envEntry3) {
        this.envEntry3 = envEntry3;
    }

    @Resource(mappedName = "4")
    public void setEnvEntry4(int envEntry4) {
        this.envEntry4 = envEntry4;
    }

    @Resource(name = "envEntry6", mappedName = "6")
    public void setEnvEntry6(int envEntry6) {
        this.envEntry6 = envEntry6;
    }
}