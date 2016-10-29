package com.baeldung.patterns.intercepting.filter.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        super.process();
        HttpSession session = request.getSession(false);
        session.removeAttribute("username");
        session.removeAttribute("order");
        response.sendRedirect("/?command=Home");
    }
}
