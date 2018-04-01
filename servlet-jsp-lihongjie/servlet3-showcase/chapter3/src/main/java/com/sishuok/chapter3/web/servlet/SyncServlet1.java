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
 * <p>Date: 13-6-22 下午10:02
 * <p>Version: 1.0
 */
@WebServlet(name = "syncServlet1", urlPatterns = "/sync1")
public class SyncServlet1 extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Connection", "Keep-Alive");
        resp.setHeader("Proxy-Connection", "Keep-Alive");
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();
        out.write("hello sync");
        out.write("<br/>");
        out.flush();

        //假设是个耗时任务，此时必须等待
        try {
            Thread.sleep(2L * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        out.write("over");

    }
}
