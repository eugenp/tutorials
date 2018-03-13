/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.web.controller.bigpage;

import com.sishuok.bigpipe.BigPipeContext;
import com.sishuok.bigpipe.Pagelet;
import com.sishuok.bigpipe.PageletResult;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-28 下午8:20
 * <p>Version: 1.0
 */
@Controller("bigpipe/part1")
public class Part1Pagelet implements Pagelet {

    @Override
    public PageletResult run(final BigPipeContext context, final HttpServletResponse response) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        context.put("msg", "这是第一段内容");

        return PageletResult.pageletResult("part1")
                .container("part1")
                .cssUrl("/static/bigpipe/part1.css")
                .jsUrl("/static/bigpipe/part1.js");
    }
}
