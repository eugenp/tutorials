package com.baeldung.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyHttpServlet extends javax.servlet.http.HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println(request.getParameter("function"));
        if (request.getParameter("function").compareTo("getContextPath") == 0) {
            writer.println(request.getContextPath());
        } else if (request.getParameter("function").compareTo("getLocalAddr") == 0) {
            writer.println(request.getLocalAddr());
        } else if (request.getParameter("function").compareTo("getLocalName") == 0) {
            writer.println(request.getLocalName());
        } else if (request.getParameter("function").compareTo("getLocalPort") == 0) {
            writer.println(request.getLocalPort());
        } else if (request.getParameter("function").compareTo("getMethod") == 0) {
            writer.println(request.getMethod());
        } else if (request.getParameter("function").compareTo("getParameterNames") == 0) {
            writer.println(request.getParameterNames());
        } else if (request.getParameter("function").compareTo("getPathInfo") == 0) {
            writer.println(request.getPathInfo());
        } else if (request.getParameter("function").compareTo("getProtocol") == 0) {
            writer.println(request.getProtocol());
        } else if (request.getParameter("function").compareTo("getQueryString") == 0) {
            writer.println(request.getQueryString());
        } else if (request.getParameter("function").compareTo("getRequestedSessionId") == 0) {
            writer.println(request.getRequestedSessionId());
        } else if (request.getParameter("function").compareTo("getRequestURI") == 0) {
            writer.println(request.getRequestURI());
        } else if (request.getParameter("function").compareTo("getRequestURL") == 0) {
            writer.println(request.getRequestURL());
        } else if (request.getParameter("function").compareTo("getScheme") == 0) {
            writer.println(request.getScheme());
        } else if (request.getParameter("function").compareTo("getServerName") == 0) {
            writer.println(request.getServerName());
        } else if (request.getParameter("function").compareTo("getServerPort") == 0) {
            writer.println(request.getServerPort());
        } else if (request.getParameter("function").compareTo("getServletPath") == 0) {
            writer.println(request.getServletPath());
        } else {
            writer.println("INVALID FUNCTION");
        }
        writer.flush();
    }
    
}

