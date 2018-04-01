/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.listener;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

/**
 *
 * 测试时开启springmvc-config.xml中的mvc:deferred-result-interceptors
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 下午9:16
 * <p>Version: 1.0
 */
@Controller
public class ListenerController {

    @RequestMapping("/listener1")
    @ResponseBody
    public DeferredResult<String> listener1() {
        final DeferredResult<String> result = new DeferredResult<String>();
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("===异步任务超时了");
            }
        });

        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                System.out.println("===异步任务完成了");
            }
        });

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

    @RequestMapping("/listener2")
    @ResponseBody
    public DeferredResult<String> listener2() {
        final DeferredResult<String> result = new DeferredResult<String>();
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("===异步任务超时了");
                result.setErrorResult("error");
            }
        });

        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                System.out.println("===异步任务完成了");
            }
        });

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


    @RequestMapping("/listener3")
    @ResponseBody
    public WebAsyncTask<String> listener3() {
        long timeout = 10L * 1000; //自定义超时时间 10秒
        final WebAsyncTask webAsyncTask = new WebAsyncTask(timeout, new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2L * 1000);
                return "success";
            }
        });

        webAsyncTask.onTimeout(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("====异步任务超时了");
                return "error";
            }
        });

        webAsyncTask.onCompletion(new Runnable() {
            @Override
            public void run() {
                System.out.println("===异步任务完成了");
            }
        });

        return webAsyncTask;
    }

    @RequestMapping("/listener4")
    @ResponseBody
    public WebAsyncTask<String> listener4() {
        long timeout = 10L * 1000; //自定义超时时间 10秒
        final WebAsyncTask webAsyncTask = new WebAsyncTask(timeout, new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(20L * 1000);
                return "success";
            }
        });

        webAsyncTask.onTimeout(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("====异步任务超时了");
                return "error";
            }
        });

        webAsyncTask.onCompletion(new Runnable() {
            @Override
            public void run() {
                System.out.println("===异步任务完成了");
            }
        });

        return webAsyncTask;
    }
}
