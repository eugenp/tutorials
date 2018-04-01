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
 * 非阻塞IO写响应数据，用于：
 * 服务器写数据的速度 快于 客户端读数据的速度
 * 即客户端读取数据速度过慢，如果服务器不是有非阻塞IO，那么服务器会在写时阻塞，等待客户端处理
 *
 * 即不需要阻塞的等待客户端读取
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-20 下午7:43
 * <p>Version: 1.0
 */
@WebServlet(name = "writeServlet", urlPatterns = "/write", asyncSupported = true)
public class WriteServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(60L * 1000);
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

        ServletOutputStream os = resp.getOutputStream();
        //通过设置WriteListener来开启非阻塞写支持
        os.setWriteListener(new MyWriteListener(os, asyncContext));

    }
}
