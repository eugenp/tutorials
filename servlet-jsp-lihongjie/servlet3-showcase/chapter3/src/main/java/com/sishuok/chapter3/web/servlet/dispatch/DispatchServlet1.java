/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet.dispatch;

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
@WebServlet(name = "dispatchServlet1", urlPatterns = "/dispatch1", asyncSupported = true)
public class DispatchServlet1 extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        if(req.getAttribute("ok") == null) {
            System.out.println("===before start async:" + req.isAsyncStarted());
            final AsyncContext asyncContext = req.startAsync();
            System.out.println("===after start async:" + req.isAsyncStarted());

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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3L * 1000);
                    } catch (InterruptedException e) {
                    }
                    req.setAttribute("ok", "true");
                    req.setAttribute("msg", "success");
                    asyncContext.dispatch();
                    System.out.println("===after dispatch before handle:" + req.isAsyncStarted());
                }
            }).start();

            return;

        } else {
            //此处通过输出可以看到调用不再是异步的了，即经过dispatch后，处理不是异步的了
            System.out.println("===after dispatch in handling:" + req.isAsyncStarted());
            resp.getWriter().write((String)req.getAttribute("msg"));
        }


    }
}
