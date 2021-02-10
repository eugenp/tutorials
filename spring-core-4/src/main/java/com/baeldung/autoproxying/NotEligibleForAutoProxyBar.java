package com.baeldung.autoproxying;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class NotEligibleForAutoProxyBar implements BeanPostProcessor {
    @Autowired
    private Foo foo;

    public String hello(String name) {
        return foo.welcome(name) + ", nice to meet you";
    }
}
