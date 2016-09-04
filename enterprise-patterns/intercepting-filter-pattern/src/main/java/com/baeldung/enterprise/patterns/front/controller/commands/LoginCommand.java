package com.baeldung.enterprise.patterns.front.controller.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            request.setAttribute("redirect", request.getRequestURL()
              .append("?").append(request.getQueryString()).toString());
            forward("login");
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", request.getParameter("username"));
            response.sendRedirect(request.getParameter("redirect"));
        }
    }
}
