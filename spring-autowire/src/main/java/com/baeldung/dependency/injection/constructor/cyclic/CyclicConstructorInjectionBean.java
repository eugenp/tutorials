package com.baeldung.dependency.injection.constructor.cyclic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CyclicConstructorInjectionBean {
    private final BeanA beanA;

    @Autowired
    public CyclicConstructorInjectionBean(BeanA beanA) {
        this.beanA = beanA;
    }
}
