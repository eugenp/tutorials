package com.baeldung.jakartaeetomcat;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CurrentDateAndTime", urlPatterns = { "/date-time" })
public class CurrentDateAndTime extends HttpServlet {

    LocalDateTime currentDate = LocalDateTime.now();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        currentDate = LocalDateTime.now();

        try (PrintWriter out = response.getWriter()) {
            out.printf("""
                <html>
                   <head> <title> Current Date And Time </title> </head>
                   <body> <h2> Servlet current date and time at %s </h2> <br/> Date and Time %s </body>
                </html>
                """, request.getContextPath(), currentDate);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}

