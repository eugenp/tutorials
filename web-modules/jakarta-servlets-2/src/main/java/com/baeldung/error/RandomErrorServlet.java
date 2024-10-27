package com.baeldung.error;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/randomError")
public class RandomErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp) {
        throw new IllegalStateException("Random error");
    }
}