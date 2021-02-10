package com.baeldung.autoproxying;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;

public class EligibleForAutoProxyBar implements BeanPostProcessor {
    @Lazy
    @Autowired
    private Foo foo;

    public String hello(String name) {
        return foo.welcome(name) + ", nice to meet you";
    }
}
