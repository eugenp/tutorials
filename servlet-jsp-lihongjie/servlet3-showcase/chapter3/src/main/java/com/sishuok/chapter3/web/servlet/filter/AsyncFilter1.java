/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 下午4:26
 * <p>Version: 1.0
 */
@WebFilter(
        filterName = "asyncFilter1",
        urlPatterns = {"/filter1", "/filter2"},
        asyncSupported = true,
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ASYNC})
public class AsyncFilter1 implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        System.out.println("====before filter1");
        chain.doFilter(request, response);
        System.out.println("====after filter1");
    }

    @Override
    public void destroy() {
    }
}
