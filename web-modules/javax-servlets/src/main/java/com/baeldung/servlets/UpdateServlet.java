package com.baeldung.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.setAttribute("userName", request.getParameter("userName"));
            session.setAttribute("age", request.getParameter("age"));

            request.setAttribute("sessionData", session);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(request, response);
    }

}
