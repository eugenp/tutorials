/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-28 下午8:11
 * <p>Version: 1.0
 */
public class BigPipeTask {

    private final Map<String, Object> model;

    private String framePageletName;
    private List<String> pageletNames;

    public BigPipeTask(final Map<String, Object> model, final String framePageletName, final String... pageletNames) {
        this.model = model;
        this.framePageletName = framePageletName;
        if(pageletNames != null && pageletNames.length > 0) {
            this.pageletNames = Arrays.asList(pageletNames);
        } else {
            this.pageletNames = Collections.EMPTY_LIST;
        }
    }

    public String getFramePageletName() {
        return framePageletName;
    }

    public List<String> getPageletNames() {
        return pageletNames;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
