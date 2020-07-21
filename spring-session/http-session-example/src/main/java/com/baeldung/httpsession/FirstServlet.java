package com.baeldung.httpsession;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FirstServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            String name = request.getParameter("userName");
            session.setAttribute("uname", name);
            out.println("Hi " + name + "  Your Session Id is :  " + session.getId() + " ");

            out.println("<br/><a href='second'>Second Servlet</a>");

            out.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
