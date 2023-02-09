package com.baeldung.caching.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class CustomKeyGenerator implements KeyGenerator {

    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getSimpleName() + "_" + method.getName() + "_"
                + StringUtils.arrayToDelimitedString(params, "_");
    }
}
