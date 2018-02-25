package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInjection {

    @Autowired
    private SimpleBean simpleBean;

    public String echo(String text) {
        return simpleBean.echo(text);
    }

}
