/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.interceptor;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptorAdapter;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-18 下午9:10
 * <p>Version: 1.0
 */
public class MyDeferredResultInterceptor extends DeferredResultProcessingInterceptorAdapter {

    @Override
    public <T> void beforeConcurrentHandling(final NativeWebRequest request, final DeferredResult<T> deferredResult) throws Exception {
        System.out.println("=====在startAsyc开启异步之前执行");
    }

    @Override
    public <T> void preProcess(final NativeWebRequest request, final DeferredResult<T> deferredResult) throws Exception {
        //不同于Callable 它与beforeConcurrentHandling在同一个线程执行
        System.out.println("在startAsyc开启异步之后执行");
    }

    @Override
    public <T> void postProcess(final NativeWebRequest request, final DeferredResult<T> deferredResult, final Object concurrentResult) throws Exception {
        //在 DeferredResult.setResult(Object) 或 DeferredResult#setErrorResult(Object) 调用之后执行
        System.out.println("设置了DeferredResult的值之后执行");

    }

    @Override
    public <T> boolean handleTimeout(final NativeWebRequest request, final DeferredResult<T> deferredResult) throws Exception {
        System.out.println("异步任务超时了");
        deferredResult.setErrorResult("error");//设置出错的结果
        return true; //如果return true 那么后续的拦截器的handleTimeout将不执行
    }

    @Override
    public <T> void afterCompletion(final NativeWebRequest request, final DeferredResult<T> deferredResult) throws Exception {
        System.out.println("异步任务完成了");
    }
}
