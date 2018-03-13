/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-21 下午9:26
 * <p>Version: 1.0
 */
@Configuration
@ComponentScan(
        value = {"com.sishuok.chapter2.**.service", "com.sishuok.chapter2.**.repository"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
        })
public class RootConfiguration {
}
