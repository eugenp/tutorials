/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe.view.velocity;

import com.sishuok.bigpipe.PageletResult;
import com.sishuok.bigpipe.view.AbstractPageletView;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-8-4 上午9:32
 * <p>Version: 1.0
 */
public abstract class AbstractVelocityPageletView extends AbstractPageletView {


    private VelocityEngine velocityEngine;
    private Template template;
    private PageletResult pageletResult;

    public PageletResult getPageletResult() {
        return pageletResult;
    }

    public void setPageletResult(final PageletResult pageletResult) {
        this.pageletResult = pageletResult;
    }

    @Override
    public void initApplicationContext(final ApplicationContext applicationContext) {
        super.initApplicationContext(applicationContext);
        if(velocityEngine == null) {
            setVelocityEngine(autodetectVelocityEngine());
        }
    }

    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    protected VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    protected Template getTemplate() {
        if(template != null) {
            return template;
        }
        return getTemplate(getUrl());
    }

    protected Template getTemplate(String url) {
        if(getEncoding() != null) {
            return getVelocityEngine().getTemplate(url);
        }
        return getVelocityEngine().getTemplate(url, getEncoding());
    }

    private VelocityEngine autodetectVelocityEngine() {
        try {
            VelocityConfig velocityConfig = getApplicationContext().getBean(VelocityConfig.class);
            return velocityConfig.getVelocityEngine();
        }
        catch (NoSuchBeanDefinitionException ex) {
            throw new ApplicationContextException("please define a VelocityConfig", ex);
        }
    }

}
