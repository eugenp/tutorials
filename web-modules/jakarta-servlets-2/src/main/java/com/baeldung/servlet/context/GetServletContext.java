package com.baeldung.servlet.context;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "get-servlet-context", value = "servlet-context")
public class GetServletContext extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext context = getServletContext();

        // Next, we’ll fetch an initialization parameter from the context
        String message = context.getInitParameter("message");

        // Finally, we’ll send a response back to the user
        response.getWriter().write(message);
    }
}
