package com.baeldung.servlets;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

/***
 * Servlet that throws HTTP error responses and runtime exceptions.
 */
@WebServlet(urlPatterns = "/randomError")
public class RandomErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp) {
        throw new IllegalStateException("Random error");
    }
}
