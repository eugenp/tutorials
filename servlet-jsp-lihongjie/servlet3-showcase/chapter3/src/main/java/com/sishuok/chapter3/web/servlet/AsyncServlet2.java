/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-22 下午10:02
 * <p>Version: 1.0
 */
@WebServlet(name = "asyncServlet2", urlPatterns = "/async2", asyncSupported = true)
public class AsyncServlet2 extends HttpServlet {

    private final ExecutorService executorService = Executors.newScheduledThreadPool(2);

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Connection", "Keep-Alive");
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();
        out.write("hello async");
        out.write("<br/>");
        out.flush();
        //1、开启异步
        final AsyncContext asyncContext = req.startAsync();
        //2、设置超时时间，如果不设置如jetty是30000L
        asyncContext.setTimeout(10L * 1000); //设置为0表示永不超时

        //把任务提交给自己的任务队列
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3L * 1000);
                    PrintWriter out = asyncContext.getResponse().getWriter();
                    out.write("over");
                    asyncContext.complete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //4、当前线程可立即返回
    }
}
