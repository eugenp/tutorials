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
 * <p>Date: 13-6-21 下午3:31
 * <p>Version: 1.0
 */
@WebServlet(name = "createSessionServlet", urlPatterns = "/createSession")
public class CreateSessionServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("create session, id : " + req.getSession().getId());
    }
}
