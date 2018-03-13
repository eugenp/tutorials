/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe.handler;

import com.sishuok.bigpipe.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-29 下午12:06
 * <p>Version: 1.0
 */
public class BigpipeTaskReturnValueHandler implements
        HandlerMethodReturnValueHandler, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private PageletFinder pageletFinder;
    private PageletViewResolver pageletViewResolver;
    private Executor executor;

    public void setExecutor(final Executor executor) {
        this.executor = executor;
    }

    public void setPageletFinder(final PageletFinder pageletFinder) {
        this.pageletFinder = pageletFinder;
    }

    public void setPageletResolver(final PageletViewResolver pageletViewResolver) {
        this.pageletViewResolver = pageletViewResolver;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(executor == null) {
            executor = Executors.newScheduledThreadPool(10);
        }
        if(pageletFinder == null) {
            pageletFinder = applicationContext.getBean(PageletFinder.class);
        }
        if(pageletViewResolver == null) {
            pageletViewResolver = applicationContext.getBean(PageletViewResolver.class);
        }

        Assert.notNull(pageletFinder, "must define a PageletFinder");
        Assert.notNull(pageletViewResolver, "must define a PageletViewResolver");
    }


    @Override
    public boolean supportsReturnType(final MethodParameter returnType) {
        return BigPipeTask.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(final Object returnValue, final MethodParameter returnType, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest) throws Exception {

        final BigPipeTask bigPipeTask = (BigPipeTask) returnValue;

        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        final HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        final DeferredResult<Void> deferredResult = new DeferredResult<Void>();

        mavContainer.setRequestHandled(true);

        WebAsyncUtils.getAsyncManager(request).startDeferredResultProcessing(deferredResult, mavContainer);

        final BigPipeContext context = new BigPipeContext(request.getContextPath(), bigPipeTask.getModel());

        //获取第一个pagelet（即框架部分） 第一个部分暂时不用异步
        final String framePageletName = bigPipeTask.getFramePageletName();
        final Pagelet framePagelet = pageletFinder.find(framePageletName);
        Assert.notNull(framePagelet, framePageletName + " pagelet not exists");

        final BigPipeContext frameContext = context.copy();
        final PageletResult framePageletResult = framePagelet.run(frameContext, response);
        final PageletView framePageletView = pageletViewResolver.resolve(framePageletResult);
        framePageletView.render(frameContext, response);

        final AtomicInteger counter = new AtomicInteger(bigPipeTask.getPageletNames().size());
        //接下来的N个pagelet使用异步任务完成
        for(String otherPageletName : bigPipeTask.getPageletNames()) {
            final Pagelet pagelet = pageletFinder.find(otherPageletName);
            Assert.notNull(pagelet, otherPageletName + " pagelet not exists");

            //然后提交队列执行
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final BigPipeContext pageletContext = context.copy();
                        final PageletResult pageletResult = pagelet.run(pageletContext, response);
                        final PageletView pageletView = pageletViewResolver.resolve(pageletResult);
                        pageletView.render(pageletContext, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //暂时忽略
                    }
                    if (counter.decrementAndGet() <= 0) {
                        deferredResult.setResult(null);
                    }
                }
            });
        }
    }
}
