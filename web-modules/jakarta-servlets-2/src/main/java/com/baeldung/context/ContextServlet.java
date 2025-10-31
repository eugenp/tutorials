package com.baeldung.context;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(ContextServlet.PATH)
public class ContextServlet extends HttpServlet {

    protected static final String PATH = "/context";

    protected static final String LABEL_FROM_HTTP_SERVLET = "1) From HttpServlet: ";
    protected static final String LABEL_FROM_SERVLET_CONFIG = "2) From ServletConfig: ";
    protected static final String LABEL_FROM_HTTP_SERVLET_REQUEST = "3) From HttpServletRequest: ";
    protected static final String LABEL_FROM_HTTP_SESSION = "4) From HttpSession: ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        resp.setContentType("text/plain");

        // 1. Direct Access From the Servlet
        ServletContext contextFromServlet = this.getServletContext();
        resp.getWriter()
            .println(LABEL_FROM_HTTP_SERVLET + contextFromServlet);

        resp.getWriter()
            .println();

        // 2. Accessing Through the ServletConfig
        ServletConfig config = this.getServletConfig();
        ServletContext contextFromConfig = config.getServletContext();
        resp.getWriter()
            .println(LABEL_FROM_SERVLET_CONFIG + contextFromConfig);

        resp.getWriter()
            .println();

        // 3. Getting the Context From the HttpServletRequest (Servlet 3.0+)
        ServletContext contextFromRequest = req.getServletContext();
        resp.getWriter()
            .println(LABEL_FROM_HTTP_SERVLET_REQUEST + contextFromRequest);

        resp.getWriter()
            .println();

        // 4. Retrieving Through the Session Object
        ServletContext contextFromSession = req.getSession()
            .getServletContext();
        resp.getWriter()
            .println(LABEL_FROM_HTTP_SESSION + contextFromSession);
    }
}
