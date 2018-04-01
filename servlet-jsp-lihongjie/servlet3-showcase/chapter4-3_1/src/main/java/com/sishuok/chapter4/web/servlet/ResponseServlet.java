/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-21 下午3:16
 * <p>Version: 1.0
 */
@WebServlet(name = "responseServlet", urlPatterns = "/response")
public class ResponseServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write("haha");

        //servlet3.1明确阐明了它的作用：清空buffer中的所有数据、以及状态码、头字段。Servlet3.1也清空getServletOutputStream 或 getWriter调用的状态
        resp.reset();

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("iso-8859-1");
        resp.getOutputStream().write("wuwu".getBytes("ISO-8859-1"));

    }
}
