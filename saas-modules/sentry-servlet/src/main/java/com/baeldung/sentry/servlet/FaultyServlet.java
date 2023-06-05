package com.baeldung.sentry.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/fault", loadOnStartup = 1)
public class FaultyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String op = req.getParameter("op");
        if( "fault".equals(op) ) {
            resp.sendError(500, "Something bad happened !");
        }
        else if ( "exception".equals(op) ) {
            throw new IllegalArgumentException("Internal error");
        }
        else {
            resp.setStatus(200);
            resp.setContentType("text/plain");
            resp.getWriter().println("OK");
        }
    }
}
