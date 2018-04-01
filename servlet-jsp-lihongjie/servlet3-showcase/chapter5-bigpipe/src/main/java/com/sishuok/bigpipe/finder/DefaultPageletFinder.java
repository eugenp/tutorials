/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe.finder;

import com.sishuok.bigpipe.Pagelet;
import com.sishuok.bigpipe.PageletFinder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-8-3 下午8:51
 * <p>Version: 1.0
 */
public class DefaultPageletFinder implements PageletFinder, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Pagelet find(final String pageletName) {
        return this.applicationContext.getBean(pageletName, Pagelet.class);
    }
}
