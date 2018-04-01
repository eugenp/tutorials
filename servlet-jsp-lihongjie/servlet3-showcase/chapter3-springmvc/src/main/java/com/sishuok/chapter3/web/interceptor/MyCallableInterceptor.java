/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.interceptor;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;

import java.util.concurrent.Callable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 下午8:23
 * <p>Version: 1.0
 */
public class MyCallableInterceptor extends CallableProcessingInterceptorAdapter {

    @Override
    public <T> void beforeConcurrentHandling(final NativeWebRequest request, final Callable<T> task) throws Exception {
        System.out.println("=====在startAsyc开启异步 和 提交异步任务到Executor 之前执行");
        //此时可以保存一些数据到异步上下文，如spring security的SecurityContextHolder或Shiro的SecurityUtils(Subject)
    }

    @Override
    public <T> void preProcess(final NativeWebRequest request, final Callable<T> task) throws Exception {
        //此时可以把在beforeConcurrentHandling保存的那些数据绑定到这个线程上（将在一个新线程执行）
        System.out.println("在Callable.call运行之前执行");
    }

    @Override
    public <T> void postProcess(final NativeWebRequest request, final Callable<T> task, final Object concurrentResult) throws Exception {
        System.out.println("在Callable.call运行之后执行");
    }

    @Override
    public <T> Object handleTimeout(final NativeWebRequest request, final Callable<T> task) throws Exception {
        //超时时调用，返回值将是超时后的结果
        System.out.println("异步任务超时了");
        return "error";//返回出错时的结果，将替换掉正常时的结果
    }

    @Override
    public <T> void afterCompletion(final NativeWebRequest request, final Callable<T> task) throws Exception {
        //异步任务正常结束时调用
        System.out.println("异步任务完成了");
    }
}
