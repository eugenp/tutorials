/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.web.controller.pipe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 *
 * Pipe思想，即完成一部门内容就刷出，缺点：
 *  1、串行输出内容
 *  2、下一个片段 必须 等待上一个片段
 *
 *  此请求需要15秒才能下载完
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-28 上午11:07
 * <p>Version: 1.0
 */
@Controller
public class PipeController {

    @RequestMapping("/pipe")
    public Callable<String> pipe() throws IOException, InterruptedException {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "pipe/index";
            }
        };

    }
}
