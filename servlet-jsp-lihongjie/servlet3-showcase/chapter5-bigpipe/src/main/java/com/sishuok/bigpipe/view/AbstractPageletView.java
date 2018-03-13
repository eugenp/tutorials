/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe.view;

import com.sishuok.bigpipe.PageletView;
import org.springframework.context.ApplicationContext;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-8-4 上午9:17
 * <p>Version: 1.0
 */
public abstract class AbstractPageletView implements PageletView {

    private ApplicationContext applicationContext;
    private String contextType;
    private String url;
    private String encoding;

    public void initApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public String getContextType() {
        return contextType;
    }

    public void setContextType(final String contextType) {
        this.contextType = contextType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }
}
