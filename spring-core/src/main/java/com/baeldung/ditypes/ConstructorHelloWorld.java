package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ConstructorHelloWorld {

    private String helloWorldBean;

    @Autowired
    ConstructorHelloWorld(String helloWorldBean) {
        this.helloWorldBean = helloWorldBean;
    }

    String getHelloWorldBean() {
        return helloWorldBean;
    }
}
