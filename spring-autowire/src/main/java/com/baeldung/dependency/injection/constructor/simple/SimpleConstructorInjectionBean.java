package com.baeldung.dependency.injection.constructor.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleConstructorInjectionBean {
    private final BeanA beanA;

    @Autowired
    public SimpleConstructorInjectionBean(BeanA beanA) {
        this.beanA = beanA;
    }

    public BeanA getBeanA() {
        return beanA;
    }
}
