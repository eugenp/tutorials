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
package org.apache.catalina.loader;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class EchoTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    private String echo = null;

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public String getEcho() {
        return echo;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("<p>" + echo + "</p>");
        } catch (IOException e) {
            throw new JspException(e);
        }
        return super.doStartTag();
    }
}
