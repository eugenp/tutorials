package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjection {

    private SimpleBean simpleBean;

    @Autowired
    public ConstructorInjection(SimpleBean simpleBean) {
        this.simpleBean = simpleBean;
    }

    public String echo(String text) {
        return simpleBean.echo(text);
    }

}
