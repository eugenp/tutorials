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
package org.apache.jasper.runtime;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

public abstract class TesterHttpJspBase extends GenericServlet implements HttpJspPage {

    private static final long serialVersionUID = 1L;


    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        _jspService((HttpServletRequest) req, (HttpServletResponse) res);
    }


    @Override
    public abstract void _jspService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jspInit();
    }


    @Override
    public void jspInit() {
        // NO-OP by default
    }


    @Override
    public void destroy() {
        super.destroy();
        jspDestroy();
    }


    @Override
    public void jspDestroy() {
        // NO-OP by default
    }
}
