package com.baeldung.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExampleOne extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html>" + "<head>" + "<meta charset=\"UTF-8\" />" + "<title>HTML Rendered by Servlet</title>" + "</head>" + "<body>" + "<h1>HTML Rendered by Servlet</h1></br>" + "<p>This page was rendered by the ExampleOne Servlet!</p>"
                + "</body>" + "</html>");
    }
}