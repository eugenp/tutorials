/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet.listener;

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
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 上午7:19
 * <p>Version: 1.0
 */
@WebServlet(name = "listenerServlet1", urlPatterns = "/listener1", asyncSupported = true)
public class ListenerServlet1 extends HttpServlet {


    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(2 * 1000);
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(final AsyncEvent event) throws IOException {
                System.out.println("=====async complete");
            }

            @Override
            public void onTimeout(final AsyncEvent event) throws IOException {
                System.out.println("=====async timeout");
                asyncContext.complete(); //需要调用下complete 否则如jetty默认每2秒重新调度一次当前方法
            }

            @Override
            public void onError(final AsyncEvent event) throws IOException {
                System.out.println("=====async error");
            }

            @Override
            public void onStartAsync(final AsyncEvent event) throws IOException {
                System.out.println("=====start new async");//第一次调用startAsync无用，而是与之后调用ServletRequest.startAsync时关联
            }
        });

    }
}
