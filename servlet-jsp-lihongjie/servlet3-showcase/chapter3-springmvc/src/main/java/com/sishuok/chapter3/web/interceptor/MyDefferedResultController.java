/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

/**
 *
 * 测试时开启springmvc-config.xml中的mvc:deferred-result-interceptors
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 下午9:16
 * <p>Version: 1.0
 */
@Controller
public class MyDefferedResultController {

    @RequestMapping("/myDefferedResult1")
    @ResponseBody
    public DeferredResult<String> myDefferedResult1() {
        final DeferredResult<String> result = new DeferredResult<String>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2 * 1000L);
                } catch (InterruptedException e) {
                }
                System.out.println("设置DeferredResult的成功时结果");
                result.setResult("success");
            }
        }).start();
        return result;
    }

    @RequestMapping("/myDefferedResult2")
    @ResponseBody
    public DeferredResult<String> myDefferedResult2() {
        final DeferredResult<String> result = new DeferredResult<String>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(31 * 1000L);
                } catch (InterruptedException e) {
                }
                System.out.println("设置DeferredResult的成功时结果（将发生在异步任务完成后执行，因为超时了，结果无效）");
                result.setResult("success");
            }
        }).start();
        return result;
    }

}
