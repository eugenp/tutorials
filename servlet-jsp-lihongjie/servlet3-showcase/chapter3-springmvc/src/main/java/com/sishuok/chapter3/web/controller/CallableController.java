/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.controller;

import com.sishuok.chapter3.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Callable;

/**
 * spring实现方式：
 *   1、把任务提交给Executor 异步执行；
 *   2、执行完成后RequestMappingHandlerAdapter使用内部的ServletInvocableHandlerMethod包装返回值，即按照非异步方式再执行
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-16 下午7:59
 * <p>Version: 1.0
 */
@Controller
public class CallableController {

    @RequestMapping("/callable1")
    public Callable<String> callable1(final Model model) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2L * 1000); //暂停两秒
                String viewName = "msg";
                model.addAttribute("msg", "hello callable");
                return viewName; //然后返回到指定视图页面
            }
        };
    }

    @RequestMapping("/callable2")
    public Callable<ModelAndView> callable2() {
        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                Thread.sleep(2L * 1000); //暂停两秒
                ModelAndView mv = new ModelAndView("msg");
                mv.addObject("msg", "hello callable");
                return mv;
            }
        };
    }

    @RequestMapping("/callable3")
    @ResponseBody
    public Callable<Object> callable3() {
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(2L * 1000); //暂停两秒
                return new User(1, "zhang");
            }
        };
    }
}
