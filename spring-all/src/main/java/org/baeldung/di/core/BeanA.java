package org.baeldung.di.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BeanA {

    @Autowired
    @Qualifier("beanB2")
    private IBeanB dependency;

}
