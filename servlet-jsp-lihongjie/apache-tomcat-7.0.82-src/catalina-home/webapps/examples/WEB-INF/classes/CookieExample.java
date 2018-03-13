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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.HTMLFilter;

/**
 * Example servlet showing request headers
 *
 * @author James Duncan Davidson <duncan@eng.sun.com>
 */

public class CookieExample extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final ResourceBundle RB = ResourceBundle.getBundle("LocalStrings");

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        String cookieName = request.getParameter("cookiename");
        String cookieValue = request.getParameter("cookievalue");
        Cookie aCookie = null;
        if (cookieName != null && cookieValue != null) {
            aCookie = new Cookie(cookieName, cookieValue);
            aCookie.setPath(request.getContextPath() + "/");
            response.addCookie(aCookie);
        }

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");

        String title = RB.getString("cookies.title");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");

        // relative links

        // XXX
        // making these absolute till we work out the
        // addition of a PathInfo issue

        out.println("<a href=\"../cookies.html\">");
        out.println("<img src=\"../images/code.gif\" height=24 " +
                    "width=24 align=right border=0 alt=\"view code\"></a>");
        out.println("<a href=\"../index.html\">");
        out.println("<img src=\"../images/return.gif\" height=24 " +
                    "width=24 align=right border=0 alt=\"return\"></a>");

        out.println("<h3>" + title + "</h3>");

        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            out.println(RB.getString("cookies.cookies") + "<br>");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                out.print("Cookie Name: " + HTMLFilter.filter(cookie.getName())
                          + "<br>");
                out.println("  Cookie Value: "
                            + HTMLFilter.filter(cookie.getValue())
                            + "<br><br>");
            }
        } else {
            out.println(RB.getString("cookies.no-cookies"));
        }

        if (aCookie != null) {
            out.println("<P>");
            out.println(RB.getString("cookies.set") + "<br>");
            out.print(RB.getString("cookies.name") + "  "
                      + HTMLFilter.filter(cookieName) + "<br>");
            out.print(RB.getString("cookies.value") + "  "
                      + HTMLFilter.filter(cookieValue));
        }

        out.println("<P>");
        out.println(RB.getString("cookies.make-cookie") + "<br>");
        out.print("<form action=\"");
        out.println("CookieExample\" method=POST>");
        out.print(RB.getString("cookies.name") + "  ");
        out.println("<input type=text length=20 name=cookiename><br>");
        out.print(RB.getString("cookies.value") + "  ");
        out.println("<input type=text length=20 name=cookievalue><br>");
        out.println("<input type=submit></form>");


        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request, response);
    }

}


