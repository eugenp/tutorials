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

@WebServlet("/welcome")
@ServletSecurity(@HttpConstraint(rolesAllowed = "USER_ROLE"))
public class WelcomeServlet extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        securityContext.hasAccessToWebResource("/protectedServlet", "GET");
        resp.getWriter().write("" +
                "Authentication type :" + req.getAuthType() + "\n" +
                "Caller Principal :" + securityContext.getCallerPrincipal() + "\n" +
                "User in Role USER_ROLE :" + securityContext.isCallerInRole("USER_ROLE") + "\n" +
                "User in Role ADMIN_ROLE :" + securityContext.isCallerInRole("ADMIN_ROLE") + "\n" +
                "");
    }
}