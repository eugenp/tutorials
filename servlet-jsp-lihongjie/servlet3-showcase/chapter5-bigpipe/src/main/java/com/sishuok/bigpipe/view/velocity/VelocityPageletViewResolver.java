/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe.view.velocity;

import com.sishuok.bigpipe.PageletResult;
import com.sishuok.bigpipe.PageletView;
import com.sishuok.bigpipe.PageletViewResolver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-8-4 上午9:07
 * <p>Version: 1.0
 */
public class VelocityPageletViewResolver implements PageletViewResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private String prefix = "";
    private String suffix = "";
    private String encoding;
    private String contentType;
    private Class<?> pageletViewClass;
    private Class<?> framePageletViewClass;


    public VelocityPageletViewResolver() {
        pageletViewClass = defaultPageletViewClass();
        framePageletViewClass = defaultFramePageletViewClass();
    }


    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public Class<?> getPageletViewClass() {
        return pageletViewClass;
    }

    public void setPageletViewClass(final Class<?> pageletViewClass) {
        this.pageletViewClass = pageletViewClass;
    }

    public Class<?> getFramePageletViewClass() {
        return framePageletViewClass;
    }

    public void setFramePageletViewClass(final Class<?> framePageletViewClass) {
        this.framePageletViewClass = framePageletViewClass;
    }

    @Override
    public PageletView resolve(final PageletResult pageletResult) {
        AbstractVelocityPageletView pageletView = null;
        if(pageletResult.isFrameResult()) {
            pageletView =
                    (AbstractVelocityPageletView)BeanUtils.instantiate(framePageletViewClass);
        } else {
            pageletView =
                    (AbstractVelocityPageletView)BeanUtils.instantiate(pageletViewClass);
        }
        pageletView.setContextType(getContentType());
        pageletView.setEncoding(getEncoding());
        pageletView.setUrl(getPrefix() + pageletResult.getViewName() + getSuffix());
        pageletView.setPageletResult(pageletResult);
        pageletView.initApplicationContext(applicationContext);

        return pageletView;
    }



    protected Class<?> defaultFramePageletViewClass() {
        return VelocityFramePageletView.class;
    }

    protected Class<?> defaultPageletViewClass() {
        return VelocityPageletView.class;
    }



}
