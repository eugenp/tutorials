package org.baeldung.boot.controller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringHelloWorldServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SpringHelloWorldServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println("SpringHelloWorldServlet: GET METHOD");
            out.flush();
        } finally {
            if (!Objects.isNull(out))
                out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println("SpringHelloWorldServlet: POST METHOD");
            out.flush();
        } finally {
            if (!Objects.isNull(out))
                out.close();
        }
    }

}
