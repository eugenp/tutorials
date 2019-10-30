package com.baeldung.javaee.security;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@WebServlet("/admin")
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"admin_role"}))
public class AdminServlet extends HttpServlet {

    @Inject
    SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("getCallerPrincipal :" + securityContext.getCallerPrincipal() + "\n");
        response.getWriter().append("CustomPrincipal :" + securityContext.getPrincipalsByType(CustomPrincipal.class) + "\n");
        response.getWriter().append("Principal :" + securityContext.getPrincipalsByType(Principal.class) + "\n");
    }
}