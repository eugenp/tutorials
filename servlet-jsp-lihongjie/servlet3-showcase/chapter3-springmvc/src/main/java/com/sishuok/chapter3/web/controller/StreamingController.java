/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * streaming
 *
 * spring mvc没有提供streaming实现，所以此处需要还原到原始做法上
 *
 * 如下代码直接复制在chapter3下的com.sishuok.chapter3.web.servlet.AsyncServlet3
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 下午8:05
 * <p>Version: 1.0
 */
@Controller
public class StreamingController {

    private final Queue<AsyncContext> queue = new ConcurrentLinkedQueue();

    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //模拟推送消息，主要实现方式两种方式：长轮询 + iframe长连接，具体自己搜索，本系列不介绍这些内容
                while (true) {
                    //一秒执行一次
                    try {
                        Thread.sleep(5L * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //注意这种方式不能用于实际环境，因为没有考虑过期情况 所以每刷新一次页面会创建一个新的AsyncContext
                    Iterator<AsyncContext> iter = queue.iterator();
                    while (iter.hasNext()) {
                        AsyncContext asyncContext = iter.next();
                        //此处应该判断超时时间，如果超时了 直接移除即可
                        try {
                            ServletResponse response = asyncContext.getResponse();
                            PrintWriter out = response.getWriter();
                            String msg = "new message : " + System.currentTimeMillis();
                            out.write("<script>parent.callback('" + msg + "');</script>");
                            try {
                                response.flushBuffer();
                            } catch (IOException e) {
                                //远程主机可能强制关闭一个链接 直接把连接移除
                                iter.remove();
                                asyncContext.complete();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
//                    System.out.println("push message count : " + queue.size());
                }
            }
        }).start();
    }


    @RequestMapping("/async3")
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Connection", "Keep-Alive");
        resp.addHeader("Cache-Control", "private");
        resp.addHeader("Pragma", "no-cache");
        resp.setContentType("text/html;charset=utf-8");
        resp.flushBuffer();

        //1、开启异步
        final AsyncContext asyncContext = req.startAsync();

        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(final AsyncEvent event) throws IOException {
                queue.remove(event.getAsyncContext());
            }

            @Override
            public void onTimeout(final AsyncEvent event) throws IOException {
                event.getAsyncContext().complete();
                queue.remove(event.getAsyncContext());
            }

            @Override
            public void onError(final AsyncEvent event) throws IOException {
                queue.remove(event.getAsyncContext());
            }

            @Override
            public void onStartAsync(final AsyncEvent event) throws IOException {
            }
        });

        //将异步上下文加入到队列中，这样在未来可以推送消息
        queue.add(asyncContext);

    }
}
