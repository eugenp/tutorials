package com.baeldung.ex.beancreationexception.cause8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanB implements IBeanB {
    final IBeanA beanA;

    @Autowired
    public BeanB(final IBeanA beanA) {
        super();
        this.beanA = beanA;
    }
}