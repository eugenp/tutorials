package com.coreservlets.demo2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet(urlPatterns = "/MyServlet.do")
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Hello hello;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        hello = new Hello();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("key");
        String message = hello.doHello(name);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/views/hello.jsp").forward(request, response);
    }

}
