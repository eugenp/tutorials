/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-25 上午7:50
 * <p>Version: 1.0
 */
@WebServlet(name = "p", urlPatterns = "/params")
public class ParamsServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        out.write("hello");
        out.flush();
        out.close();


        System.out.println(req.getParameter("name"));

    }
}
