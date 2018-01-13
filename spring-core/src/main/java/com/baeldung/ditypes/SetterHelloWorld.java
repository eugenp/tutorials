package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SetterHelloWorld {

    private String helloWorldBean;

    String getHelloWorldBean() {
        return helloWorldBean;
    }

    @Autowired
    void setHelloWorldBean(String helloWorldBean) {
        this.helloWorldBean = helloWorldBean;
    }
}
