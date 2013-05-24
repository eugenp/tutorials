package org.baeldung.web;

import org.springframework.stereotype.Component;

@Component
public class BeanA {

    private IBeanC dependency;

    public BeanA() {
        super();
    }

    //

}
