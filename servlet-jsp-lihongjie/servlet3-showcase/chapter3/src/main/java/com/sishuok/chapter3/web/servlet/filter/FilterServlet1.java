/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet.filter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 分派到自己，此时需要一个flag判断当前需要启动异步，还是进行同步处理
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 下午3:46
 * <p>Version: 1.0
 */
@WebServlet(name = "filterServlet1", urlPatterns = "/filter1", asyncSupported = true)
public class FilterServlet1 extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("====filter servlet 1  before");

        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(10L * 1000);
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(final AsyncEvent event) throws IOException {
                System.out.println("=====async complete");
            }

            @Override
            public void onTimeout(final AsyncEvent event) throws IOException {
            }

            @Override
            public void onError(final AsyncEvent event) throws IOException {
            }

            @Override
            public void onStartAsync(final AsyncEvent event) throws IOException {
            }
        });

        System.out.println("====filter servlet 1  before dispatch");
        //异步下分派过去的请求，异步filter也可以拦截
        //如果filter不是DispatcherType.ASYNC 类型，那么分派到/filter2时，拦截器不会调用
        asyncContext.dispatch("/filter2");
        System.out.println("====filter servlet 1  after dispatch");

        System.out.println("====filter servlet 1  after");


    }
}
