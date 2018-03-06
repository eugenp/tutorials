/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-22 下午1:52
 * <p>Version: 1.0
 */
@WebServlet(name = "apiServlet", urlPatterns = "/api")
public class ApiServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        //以前
        req.getSession().getServletContext();
        //现在
        ServletContext sc = req.getServletContext();

        //servlet主要版本
        System.out.println(sc.getEffectiveMajorVersion());
        //servlet次要版本
        System.out.println(sc.getEffectiveMinorVersion());



        //默认的session跟踪机制
        System.out.println(sc.getDefaultSessionTrackingModes());
        //有效的session跟踪机制
        System.out.println(sc.getEffectiveSessionTrackingModes());
        //设置session跟踪机制：有COOKIE URL SSL
        //需要在容器初始化时 完成 如ServletContextListener#contextInitialized方法中调用如下代码 具体看javadoc
//        Set<SessionTrackingMode> sessionTrackingModes = new HashSet<SessionTrackingMode>();
//        sessionTrackingModes.add(SessionTrackingMode.COOKIE);
//        sc.setSessionTrackingModes(sessionTrackingModes);

        //用于session跟踪的cookie配置，比如默认Name是JSESSIONID，可以修改之
        SessionCookieConfig sessionCookieConfig = sc.getSessionCookieConfig();
        System.out.println(sessionCookieConfig.getName());

        //把默认的JSESSIONID--修改为->id   可以观察客户端变成了id
        sessionCookieConfig.setName("id");

        //得到请求的session id
        req.getRequestedSessionId();

        /**得到分派的类型 请参考：{@link javax.servlet.DispatcherType}*/
        System.out.println(req.getDispatcherType());


        Cookie cookie = new Cookie("key", "value");
        //servlet 3，功能是禁止客户端脚本访问
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);


        //得到响应的状态码
        resp.getStatus();
        //得到响应头
//        resp.getHeader();
//        resp.getHeaderNames();
//        resp.getHeaders();




    }
}
