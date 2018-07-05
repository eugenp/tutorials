package com.baeldung.servlets;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static javax.servlet.RequestDispatcher.*;

@WebServlet(urlPatterns = "/errorHandler")
public class ErrorHandlerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<html><head><title>Error description</title></head><body>");
            writer.write("<h2>Error description</h2>");
            writer.write("<ul>");
            Arrays.asList(ERROR_STATUS_CODE, ERROR_EXCEPTION_TYPE, ERROR_MESSAGE)
                    .forEach(e ->
                            writer.write("<li>" + e + ":" + req.getAttribute(e) + " </li>")
                    );
            writer.write("</ul>");
            writer.write("</html></body>");
        }

        Exception exception = (Exception) req.getAttribute(ERROR_EXCEPTION);
        if (IllegalArgumentException.class.isInstance(exception)) {
            getServletContext().log("Error on an application argument", exception);
        }
    }
}