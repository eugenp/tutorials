package org.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanA {

    @Autowired
    private IBeanB b;

    public BeanA() {
        super();
    }

}
