package com.baeldung.dependency.injection.constructor.cyclic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanA {
    private final CyclicConstructorInjectionBean dependency;

    @Autowired
    public BeanA(CyclicConstructorInjectionBean dependency) {
        this.dependency = dependency;
    }
}
