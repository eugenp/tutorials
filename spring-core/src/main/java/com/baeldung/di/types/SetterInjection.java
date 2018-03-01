package com.baeldung.di.types;

import org.springframework.beans.factory.annotation.Autowired;

public class SetterInjection {
    private BeanB beanB;

    @Autowired
    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}
