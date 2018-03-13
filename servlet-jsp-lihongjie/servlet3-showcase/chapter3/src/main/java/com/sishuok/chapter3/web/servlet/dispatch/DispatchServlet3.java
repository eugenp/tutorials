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
@WebServlet(name = "dispatchServlet3", urlPatterns = "/dispatch3", asyncSupported = true)
public class DispatchServlet3 extends HttpServlet {

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
                    System.out.println("=====async timeout");
                }

                @Override
                public void onError(final AsyncEvent event) throws IOException {
                    System.out.println("=====async error");
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
            throw new RuntimeException("出错了");
            //如果抛出异常，容器负责最后调用asyncContext.complete()
            /**具体请参考{@link javax.servlet.AsyncContext#dispatch}的javadoc，而且一定要看*/

            /**
             * 在javadoc中有如下一段，意思就是如果抛出异常，需要回调 AsyncListener#onError但实际jetty实现是没有回调的
             * <p>Any errors or exceptions that may occur during the execution
             * of this method must be caught and handled by the container, as
             * follows:
             * <ol>
             * <li>Invoke, at their {@link AsyncListener#onError onError} method, all
             * {@link AsyncListener} instances registered with the ServletRequest
             * for which this AsyncContext was created, and make the caught
             * <tt>Throwable</tt> available via {@link AsyncEvent#getThrowable}.</li>
             */


         }


    }
}
