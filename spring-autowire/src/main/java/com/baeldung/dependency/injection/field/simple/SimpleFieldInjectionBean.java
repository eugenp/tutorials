package com.baeldung.dependency.injection.field.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleFieldInjectionBean {
    @Autowired
    private BeanA beanA;

    public BeanA getBeanA() {
        return beanA;
    }
}
