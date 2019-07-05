package com.baeldung.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyHttpServlet extends javax.servlet.http.HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        PrintWriter writer = response.getWriter();
        writer.println("getContextPath()");
        writer.println(request.getContextPath());
        writer.println("getLocalAddr()");
        writer.println(request.getLocalAddr());
        writer.println("getLocalName()");
        writer.println(request.getLocalName());
        writer.println("getLocalPort()");
        writer.println(request.getLocalPort());
        writer.println("getMethod()");
        writer.println(request.getMethod());
        writer.println("getParameterNames()");
        writer.println(request.getParameterNames());
        writer.println("getPathInfo()");
        writer.println(request.getPathInfo());
        writer.println("getProtocol()");
        writer.println(request.getProtocol());
        writer.println("getQueryString()");
        writer.println(request.getQueryString());
        writer.println("getRequestedSessionId()");
        writer.println(request.getRequestedSessionId());
        writer.println("getRequestURI()");
        writer.println(request.getRequestURI());
        writer.println("getRequestURL()");
        writer.println(request.getRequestURL());
        writer.println("getScheme()");
        writer.println(request.getScheme());
        writer.println("getServerName()");
        writer.println(request.getServerName());
        writer.println("getServerPort()");
        writer.println(request.getServerPort());
        writer.println("getServletPath()");
        writer.println(request.getServletPath());
        writer.flush();
    }
    
}
