package com.baeldung.jsp;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ExampleThree", description = "JSP Servlet With Annotations", urlPatterns = { "/ExampleThree" })
public class ExampleThree extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private String instanceMessage;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        instanceMessage = request.getParameter("message");
        request.setAttribute("text", message);
        request.setAttribute("unsafeText", instanceMessage);
        request.getRequestDispatcher("/jsp/ExampleThree.jsp").forward(request, response);
    }
}
