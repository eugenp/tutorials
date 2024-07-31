package com.baeldung.servlets;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/randomError")
public class RandomErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp) {
        throw new IllegalStateException("Random error");
    }
}