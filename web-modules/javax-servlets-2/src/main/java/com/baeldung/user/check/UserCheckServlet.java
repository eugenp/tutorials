package com.baeldung.user.check;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "home", urlPatterns = { "/user-check/", "/user-check", "/user-check/home" })
public class UserCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new IllegalStateException("user not logged in");
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);

        UserCheckFilter.forward(request, response, "/home.jsp");
    }
}
