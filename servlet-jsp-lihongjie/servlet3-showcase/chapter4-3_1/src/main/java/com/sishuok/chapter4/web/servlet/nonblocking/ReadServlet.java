/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.servlet.nonblocking;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 非阻塞IO读请求数据，用于：
 * 服务器读数据的速度 快于 当客户端写数据的速度
 *
 * 即如果客户端写入速度很慢时，如果使用阻塞I/O，此刻同步等待，浪费一个线程，如果使用非阻塞I/O，可以在数据可用时得到回调，提高并发
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-20 下午2:27
 * <p>Version: 1.0
 */
@WebServlet(name = "readServlet", urlPatterns = "/read", asyncSupported = true)
public class ReadServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        //启动异步
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(60L * 1000);
        ServletInputStream is = req.getInputStream();
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(final AsyncEvent event) throws IOException {
                System.out.println("===异步完成了");
            }

            @Override
            public void onTimeout(final AsyncEvent event) throws IOException {
                System.out.println("===异步超时了");
                asyncContext.complete();
            }

            @Override
            public void onError(final AsyncEvent event) throws IOException {
                System.out.println("===异步出错了");
                asyncContext.complete();
            }

            @Override
            public void onStartAsync(final AsyncEvent event) throws IOException {

            }
        });
        //通过设置ReadListener来开启非阻塞读支持
        is.setReadListener(new MyReadListener(is, asyncContext));

    }
}
