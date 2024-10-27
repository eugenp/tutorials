package com.baeldung;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
