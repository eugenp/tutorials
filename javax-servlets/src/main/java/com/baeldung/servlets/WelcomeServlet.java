package com.baeldung.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adam.
 */
@WebServlet(name = "WelcomeServlet", urlPatterns = "/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CookieReader cookieReader = new CookieReader(request);
        String uiColor = cookieReader.readCookie("uiColor");
        String userName = cookieReader.readCookie("userName");

        if (userName == null || userName.isEmpty()) {
            response.sendRedirect("/login");
        } else {
            request.setAttribute("uiColor", uiColor != null ? uiColor : "blue");
            request.setAttribute("userName", userName);
            request.setAttribute("sessionAttribute", request.getSession().getAttribute("sampleKey"));

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
