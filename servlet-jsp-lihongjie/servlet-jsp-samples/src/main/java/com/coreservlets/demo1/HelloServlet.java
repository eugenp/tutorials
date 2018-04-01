package com.coreservlets.demo1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lihongjie on 5/2/17.
 * Simple servlet used to test server.
 */
@WebServlet(urlPatterns = "/HelloServlet")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String docType = "<!DOCTYPE HTML PUBLIC\n";
        out.println(docType +
                "<HTML>\n" +
                "<HEAD><title>Hello</title></HEAD>\n" +
                "<body bgcolor=\"#FDF5E6\">\n" +
                "<h1>Hello</h1>\n" +
                "</body></HTML>");
    }
}
