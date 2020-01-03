package com.baeldung.ex.beancreationexception.cause8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanA implements IBeanA {
    private IBeanB beanB;

    @Autowired
    public BeanA(final IBeanB beanB) {
        super();
        this.beanB = beanB;
    }

}