package com.baeldung.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MyHttpServlet", urlPatterns = "/servlet-mapping")
public class MyHttpServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println(request.getParameter("function"));
        if ("getContextPath".equals(request.getParameter("function"))) {
            writer.println(request.getContextPath());
        } else if ("getLocalAddr".equals(request.getParameter("function"))) {
            writer.println(request.getLocalAddr());
        } else if ("getLocalName".equals(request.getParameter("function"))) {
            writer.println(request.getLocalName());
        } else if ("getLocalAPort".equals(request.getParameter("function"))) {
            writer.println(request.getLocalPort());
        } else if ("getMethod".equals(request.getParameter("function"))) {
            writer.println(request.getMethod());
        } else if ("getParameterNames".equals(request.getParameter("function"))) {
            writer.println(request.getParameterNames());
        } else if ("getPathInfo".equals(request.getParameter("function"))) {
            writer.println(request.getPathInfo());
        } else if ("getProtocol".equals(request.getParameter("function"))) {
            writer.println(request.getProtocol());
        } else if ("getQueryString".equals(request.getParameter("function"))) {
            writer.println(request.getQueryString());
        } else if ("getRequestedSessionId".equals(request.getParameter("function"))) {
            writer.println(request.getRequestedSessionId());
        } else if ("getRequestURI".equals(request.getParameter("function"))) {
            writer.println(request.getRequestURI());
        } else if ("getRequestURL".equals(request.getParameter("function"))) {
            writer.println(request.getRequestURL());
        } else if ("getScheme".equals(request.getParameter("function"))) {
            writer.println(request.getScheme());
        } else if ("getServerName".equals(request.getParameter("function"))) {
            writer.println(request.getServerName());
        } else if ("getServerPort".equals(request.getParameter("function"))) {
            writer.println(request.getServerPort());
        } else if ("getServletPath".equals(request.getParameter("function"))) {
            writer.println(request.getServletPath());
        } else {
            writer.println("INVALID FUNCTION");
        }
        writer.flush();
    }
    
}


