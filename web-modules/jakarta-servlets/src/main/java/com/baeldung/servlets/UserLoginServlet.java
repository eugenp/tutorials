package com.baeldung.servlets;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/u_login")
public class UserLoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.setAttribute("userId", request.getParameter("userId"));

        request.setAttribute("id", session.getAttribute("userId"));

        request.getRequestDispatcher("/WEB-INF/jsp/userlogin.jsp").forward(request, response);

    }

}
