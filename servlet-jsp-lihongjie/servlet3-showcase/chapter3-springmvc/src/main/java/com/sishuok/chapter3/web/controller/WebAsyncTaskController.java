/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Callable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-16 下午9:25
 * <p>Version: 1.0
 */
@Controller
public class WebAsyncTaskController {

    @RequestMapping("/webAsyncTask1")
    public WebAsyncTask<String> webAsyncTask1(final Model model) {
        long timeout = 10L * 1000; //自定义超时时间 10秒
        WebAsyncTask webAsyncTask = new WebAsyncTask(timeout, new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2L * 1000);
                model.addAttribute("msg", "hello web async task");
                String viewName = "msg";
                return viewName;
            }
        });
        return webAsyncTask;
    }

    @RequestMapping("/webAsyncTask2")
    public WebAsyncTask<String> webAsyncTask2(final Model model) {
        long timeout = 10L * 1000; //自定义超时时间 1秒
        WebAsyncTask webAsyncTask = new WebAsyncTask(timeout, new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2L * 1000);
                throw new RuntimeException("出错了");
            }
        });
        return webAsyncTask;
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception e) {
        ModelAndView mv = new ModelAndView("exception");
        mv.addObject("exception", e);
        return mv;
    }

}
