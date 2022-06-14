package com.baeldung.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserServlet", urlPatterns = "/userServlet", initParams={
    @WebInitParam(name="name", value="Not provided"), 
    @WebInitParam(name="email", value="Not provided")})
public class UserServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        forwardRequest(request, response, "/WEB-INF/jsp/result.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("name", getRequestParameter(request, "name"));
        request.setAttribute("email", getRequestParameter(request, "email"));
        request.setAttribute("province", getContextParameter("province"));
        request.setAttribute("country", getContextParameter("country"));
    }
    
    protected String getRequestParameter(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        return !param.isEmpty() ? param : getInitParameter(name);
    }
    
    protected String getContextParameter(String name) {
        return getServletContext().getInitParameter(name);
    }
    
    protected void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path) 
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}