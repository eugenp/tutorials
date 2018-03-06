/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * DeferredResult：即延迟的结果，即延迟发送结果；其实和callable类似，但是可以使用其他线程协作来通知客户
 *
 * 可实现长轮询
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-17 上午7:13
 * <p>Version: 1.0
 */
@Controller
public class DeferredResultController {

    private Queue<DeferredResult> queue = new ConcurrentLinkedQueue<DeferredResult>();


    @RequestMapping("/message")
    @ResponseBody
    public DeferredResult<String> newMessage() throws IOException {

        //1、创建DeferredResult
        long timeout = 30L * 1000;
        final DeferredResult<String> result = new DeferredResult<String>(timeout);

        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                queue.remove(result);
            }
        });

        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                queue.remove(result);
            }
        });

        //2、加到消息队列，用于消息推送
        queue.add(result);
        return result;
    }

    @Scheduled(fixedRate = 5L * 1000)
    public void pushMessage() {
        Iterator<DeferredResult> iter = queue.iterator();
        while (iter.hasNext()) {
            DeferredResult<String> result = iter.next();
            result.setResult("new message : " + System.currentTimeMillis());
        }
    }
}
