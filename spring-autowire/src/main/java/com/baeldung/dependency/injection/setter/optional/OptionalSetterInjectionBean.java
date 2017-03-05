package com.baeldung.dependency.injection.setter.optional;

import org.springframework.beans.factory.annotation.Autowired;

public class OptionalSetterInjectionBean {
    private BeanA dependency;

    public BeanA getDependency() {
        return dependency;
    }

    @Autowired(required = false)
    public void setDependency(BeanA dependency) {
        this.dependency = dependency;
    }
}
