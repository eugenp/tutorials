package com.baeldung.servlets;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

@WebServlet(name = "FormDataServlet", urlPatterns = "/form-data")
public class FormDataServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstName = StringEscapeUtils.escapeHtml4(req.getParameter("first_name"));
        String lastName = StringEscapeUtils.escapeHtml4(req.getParameter("last_name"));

        // Don't use this code as-is! It is vulnerable to XSS.
        // See
        // https://www.baeldung.com/cs/cross-site-scripting-xss-explained and
        // https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html
        // to learn how to harden it.
        resp.getWriter()
            .append("Full Name: ")
            .append(firstName)
            .append(" ")
            .append(lastName);
    }
}
