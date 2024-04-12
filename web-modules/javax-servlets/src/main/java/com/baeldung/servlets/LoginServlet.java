package com.baeldung.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by adam.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CookieReader cookieReader = new CookieReader(request);
        Optional<String> uiColor = cookieReader.readCookie("uiColor");
        Optional<String> userName = cookieReader.readCookie("userName");

        request.setAttribute("uiColor", uiColor.orElse("blue"));

        if (!userName.isPresent()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("/welcome");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("sampleKey", "Sample Value");

        String uiColor = request.getParameter("color");
        String userName = request.getParameter("name");

        Cookie uiColorCookie = new Cookie("uiColor", uiColor);
        response.addCookie(uiColorCookie);

        Cookie userNameCookie = new Cookie("userName", userName);
        response.addCookie(userNameCookie);

        response.sendRedirect("/welcome");
    }

}
