package com.baeldung.mvc.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baeldung.mvc.beans.User;
import com.baeldung.mvc.helper.LoginHelper;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userid");
        String password = request.getParameter("password");
        RequestDispatcher rd = null;
        if (LoginHelper.authenticateUser(userId, password)) {
            // Add User bean to request context, for accessing its data
            // in JSP
            User loginUser = new User();
            loginUser.setUserId(userId);
            request.setAttribute("loginUser", loginUser);
            // Redirect to Welcome page, if authentication succeeds
            rd = request.getRequestDispatcher("welcome.jsp");
        } else {
            // Redirect to Error page, if authentication fails
            rd = request.getRequestDispatcher("error.jsp");
        }
        rd.forward(request, response);
    }

    /**
     * Redirect to Login page
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
    }

}
