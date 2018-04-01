/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Callable;

/**
 * 即使使用异步，出现异常时 和普通springmvc处理方式是一样的
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-16 下午9:19
 * <p>Version: 1.0
 */
@Controller
public class ExceptionController {

    @RequestMapping("/exception")
    public Callable<String> exception() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2L * 1000);
                throw new RuntimeException("出错了");
            }
        };
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(RuntimeException e) {
        ModelAndView mv = new ModelAndView("exception");
        mv.addObject("exception", e);
        return mv;
    }

}
