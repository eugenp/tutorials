package com.baeldung.dependency.injection.field.cyclic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CyclicFieldInjectionBean {
    @Autowired
    private BeanA beanA;

    public BeanA getBeanA() {
        return beanA;
    }
}
