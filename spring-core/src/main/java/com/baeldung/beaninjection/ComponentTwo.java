package com.baeldung.beaninjection;

import org.springframework.stereotype.Component;

@Component
public class ComponentTwo {

    public String componentMethod() {
        return "componentTwo";
    }
}
