package com.baeldung.javaee.security;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"admin_role"}))
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().append("User :" + request.getUserPrincipal().getName() + "\n");
        response.getWriter().append("User in Role user_role :" + request.isUserInRole("user_role") + "\n");
        response.getWriter().append("User in Role admin_role :" + request.isUserInRole("admin_role"));
    }
}