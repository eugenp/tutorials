package com.baeldung.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by adam.
 */
@WebServlet(name = "WelcomeServlet", urlPatterns = "/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CookieReader cookieReader = new CookieReader(request);
        Optional<String> uiColor = cookieReader.readCookie("uiColor");
        Optional<String> userName = cookieReader.readCookie("userName");

        if (!userName.isPresent()) {
            response.sendRedirect("/login");
        } else {
            request.setAttribute("uiColor", uiColor.orElse("blue"));
            request.setAttribute("userName", userName.get());
            request.setAttribute("sessionAttribute", request.getSession()
                .getAttribute("sampleKey"));

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie userNameCookieRemove = new Cookie("userName", "");
        userNameCookieRemove.setMaxAge(0);
        response.addCookie(userNameCookieRemove);

        response.sendRedirect("/login");
    }

}
