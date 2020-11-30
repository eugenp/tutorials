package com.baeldung.jsp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
