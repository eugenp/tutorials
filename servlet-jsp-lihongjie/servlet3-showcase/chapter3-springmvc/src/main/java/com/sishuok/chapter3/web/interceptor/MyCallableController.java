/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

/**
 *
 * 测试时开启springmvc-config.xml中的mvc:callable-interceptors
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 下午9:02
 * <p>Version: 1.0
 */
@Controller
public class MyCallableController {

    @RequestMapping("/myCallable1")
    @ResponseBody
    public Callable<String> callable1() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("====执行Callable call");
                return "success";
            }
        };
    }
    @RequestMapping("/myCallable2")
    @ResponseBody
    public Callable<String> callable2() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(31L * 1000);
                System.out.println("====执行Callable call");
                return "success";
            }
        };
    }
}
