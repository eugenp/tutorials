package com.coreservlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lihongjie on 5/2/17.
 */
public class ServletTemplate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Use "request" to read incoming HTTP headers (e.g., cookies) and query data from HTML forms.
        // Use "response" to specify the HTTP response status code and headers (e.g., the content type, cookies).

        PrintWriter out = resp.getWriter();
        // Use "out" to send content to browser
    }
}
