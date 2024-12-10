package com.baeldung.servlet.context;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletRequest extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
        ServletContext context = servletRequest.getServletContext();

        // Next, we’ll fetch an initialization parameter from the context
        String message = context.getInitParameter("message");

        // Finally, we’ll send a response back to the user
        servletResponse.getWriter().append(message);
    }
}