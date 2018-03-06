/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-8-3 下午9:01
 * <p>Version: 1.0
 */
public class BigPipeContext extends HashMap {

    private final String contextPath;

    public BigPipeContext(final String contextPath, final Map model) {
        this.contextPath = contextPath;
        put("contextPath", contextPath);
        if(model != null) {
            this.putAll(model);
        }
    }

    public String getContextPath() {
        return contextPath;
    }

    public BigPipeContext copy() {
        BigPipeContext newContext = new BigPipeContext(this.contextPath, null);
        newContext.putAll(this);
        return newContext;
    }
}
