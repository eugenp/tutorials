package com.baeldung.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

@WebServlet(name = "FormDataServlet", urlPatterns = "/form-data")
public class FormDataServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstName = StringEscapeUtils.escapeHtml4(req.getParameter("first_name"));
        String lastName = StringEscapeUtils.escapeHtml4(req.getParameter("last_name"));

        resp.getWriter()
            .append("Full Name: ")
            .append(firstName)
            .append(" ")
            .append(lastName);
    }
}
