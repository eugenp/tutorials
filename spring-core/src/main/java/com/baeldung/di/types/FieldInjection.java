package com.baeldung.di.types;

import org.springframework.beans.factory.annotation.Autowired;

public class FieldInjection {
    @Autowired
    private BeanB beanB;

    // ...
}
