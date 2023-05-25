package com.baeldung.user.check;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user-check/login")
public class UserCheckLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) != null) {
            response.sendRedirect(request.getContextPath() + "/user-check/home");
            return;
        }

        String referer = (String) request.getAttribute("origin");
        request.setAttribute("origin", referer);
        UserCheckFilter.forward(request, response, "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("name");
        String pass = request.getParameter("password");

        User user = User.DB.get(key);
        if (user == null || !user.getPassword()
            .equals(pass)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("origin", request.getParameter("origin"));
            request.setAttribute("error", "invalid login");
            UserCheckFilter.forward(request, response, "/login.jsp");
            return;
        }

        user.getLogins()
            .add(new Date());

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        String origin = request.getParameter("origin");
        if (origin == null || origin.contains("login"))
            origin = "./";

        response.sendRedirect(origin);
    }
}
