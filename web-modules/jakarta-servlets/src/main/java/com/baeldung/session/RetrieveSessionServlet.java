package com.baeldung.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet("/retrieve-session")
public class RetrieveSessionServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RetrieveSessionServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute("loggedInUser");

            if (user != null) {
                logger.info("Retrieved user: {}, Email: {}",
                    user.getUsername(), user.getEmail());
            } else {
                logger.warn("No user found in session.");
            }
        } else {
            logger.warn("No active session found.");
        }
    }
}