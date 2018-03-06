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
package examples;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Display the sources of the JSP file.
 */
public class ShowSource extends TagSupport {

    private static final long serialVersionUID = 1L;

    String jspFile;

    public void setJspFile(String jspFile) {
        this.jspFile = jspFile;
    }

    @Override
    public int doEndTag() throws JspException {
        if ((jspFile.indexOf( ".." ) >= 0) ||
            (jspFile.toUpperCase(Locale.ENGLISH).indexOf("/WEB-INF/") != 0) ||
            (jspFile.toUpperCase(Locale.ENGLISH).indexOf("/META-INF/") != 0))
            throw new JspTagException("Invalid JSP file " + jspFile);

        InputStream in = pageContext.getServletContext().getResourceAsStream(
                jspFile);
        if (in == null)
            throw new JspTagException("Unable to find JSP file: " + jspFile);

        try {
            JspWriter out = pageContext.getOut();
            out.println("<body>");
            out.println("<pre>");
            for (int ch = in.read(); ch != -1; ch = in.read())
                if (ch == '<')
                    out.print("&lt;");
                else
                    out.print((char) ch);
            out.println("</pre>");
            out.println("</body>");
        } catch (IOException ex) {
            throw new JspTagException("IOException: " + ex.toString());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new JspTagException("Can't close inputstream: ", e);
            }
        }
        return super.doEndTag();
    }
}


