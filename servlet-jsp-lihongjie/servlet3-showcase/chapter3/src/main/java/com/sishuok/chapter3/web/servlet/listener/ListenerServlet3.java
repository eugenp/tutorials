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
import java.io.OutputStream;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 上午7:19
 * <p>Version: 1.0
 */
@WebServlet(name = "listenerServlet3", urlPatterns = "/listener3", asyncSupported = true)
public class ListenerServlet3 extends HttpServlet {


    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(10 * 1000);

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
                //如果有队列请从队列中移除
            }

            @Override
            public void onStartAsync(final AsyncEvent event) throws IOException {
                System.out.println("=====start new async");
            }
        });


        /**
         * 以jetty为例
         *
         * 1、当响应流关闭了，如果还写指定长度个字节，org.eclipse.jetty.server.HttpOutput会抛出EofException
         *
         * org.eclipse.jetty.server.HttpOutput会
         * if (isClosed())
         *    throw new EofException("Closed");
         *
         * 2、org.eclipse.jetty.server.HttpChannel会调用org.eclipse.jetty.server.HttpChannelState的error方法设置
         *   _event.setThrowable(th);
         *
         * 3、当调用asyncContext.complete时，最终会调用HttpChannelState的completed方法：其内部
         *  如果有异常调用onError 否则onComplete
         *    if (event!=null && event.getThrowable()!=null)
         *    {
         *        event.getSuppliedRequest().setAttribute(RequestDispatcher.ERROR_EXCEPTION,event.getThrowable());
         *        event.getSuppliedRequest().setAttribute(RequestDispatcher.ERROR_MESSAGE,event.getThrowable().getMessage());
         *        listener.onError(event);
         *    }
         *    else
         *       listener.onComplete(event);
         *
         * 4、也就是说 只要流程正确 不会有这个问题
         */

        final OutputStream os = resp.getOutputStream();
        os.write(new byte[] {1}, 0, 1);
        os.close();
        os.write(new byte[] {1}, 0, 1);
    }
}
