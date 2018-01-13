package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class FieldHelloWorld {

    @Autowired
    private String helloWorldBean;

    String getHelloWorldBean() {
        return helloWorldBean;
    }
}
