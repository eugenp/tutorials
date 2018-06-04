package com.baeldung.servlets;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static javax.servlet.http.HttpServletResponse.*;

/***
 * Servlet that throws HTTP error responses and runtime exceptions.
 */
@WebServlet(name = "RandomErrorServlet", urlPatterns = "/randomError")
public class RandomErrorServlet extends HttpServlet {
    interface Action {
        void execute() throws IOException;
    }

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        List<Action> errors = Arrays.asList(
                () -> resp.sendError(SC_NOT_FOUND),
                () -> resp.sendError(SC_FORBIDDEN),
                () -> System.out.println(10 / 0),
                () -> System.out.println("abc".substring(10))
        );
        errors.get(new Random().nextInt(errors.size()))
                .execute();
    }


}
