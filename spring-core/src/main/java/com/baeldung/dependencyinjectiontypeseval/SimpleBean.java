package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.stereotype.Component;

@Component
public class SimpleBean {

    public String echo(String text) {
        return text;
    }

}
