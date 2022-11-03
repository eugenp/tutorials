package com.baeldung.patterns.intercepting.filter.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", request.getParameter("username"));
            response.sendRedirect(request.getParameter("redirect"));
        } else {
            String queryString = Optional.ofNullable(request.getQueryString())
              .orElse("command=Home");
            request.setAttribute("redirect", request.getRequestURL()
              .append("?").append(queryString).toString());
            forward("login");
        }
    }
}
