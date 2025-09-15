package com.baeldung.context;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/context")
public class ContextServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        resp.setContentType("text/plain");

        // 1. Direct Access From the Servlet
        ServletContext contextFromServlet = this.getServletContext();
        resp.getWriter()
            .println("1) From HttpServlet: " + contextFromServlet);

        resp.getWriter()
            .println();

        // 2. Accessing Through the ServletConfig
        ServletConfig config = this.getServletConfig();
        ServletContext contextFromConfig = config.getServletContext();
        resp.getWriter()
            .println("2) From ServletConfig: " + contextFromConfig);

        resp.getWriter()
            .println();

        // 3. Getting the Context From the HttpServletRequest (Servlet 3.0+)
        ServletContext contextFromRequest = req.getServletContext();
        resp.getWriter()
            .println("3) From HttpServletRequest: " + contextFromRequest);

        resp.getWriter()
            .println();

        // 4. Retrieving Through the Session Object
        ServletContext contextFromSession = req.getSession()
            .getServletContext();
        resp.getWriter()
            .println("4) From HttpSession: " + contextFromSession);
    }
}
