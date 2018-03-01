package com.baeldung.di.types;

import org.springframework.beans.factory.annotation.Autowired;

public class ConstrutorInjection {
    private final BeanB beanB;

    @Autowired // optional
    public ConstrutorInjection(final BeanB beanB) {
        this.beanB = beanB;
    }
}
