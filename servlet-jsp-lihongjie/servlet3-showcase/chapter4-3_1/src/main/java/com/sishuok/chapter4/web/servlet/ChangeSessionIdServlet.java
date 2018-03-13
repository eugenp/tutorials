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
@WebServlet(name = "changeSessionIdServlet", urlPatterns = "/changeSessionId")
public class ChangeSessionIdServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        //可以访问 /createSession 创建一个session

        //得到请求时的session id（请求时不一定带着）
        System.out.println("old:" + req.getRequestedSessionId());
        //调用时 必须有session 即request.getSession(false) != null 否则IllegalStateException
        req.changeSessionId(); //比如使用URL重写时，可以在验证时更改session id，防止会话固定攻击
        System.out.println("new:" + req.getSession().getId());
    }
}
