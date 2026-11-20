package com.baeldung.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet("/store-session")
public class StoreSessionServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(StoreSessionServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = new User("john_doe", "john@example.com");
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);

        logger.info("User '{}' stored in session with ID: {}",
            user.getUsername(), session.getId());
    }
}