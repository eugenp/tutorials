package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExampleWithPropertyInjection {

    @Autowired
    private ExampleService exampleService;

    public String verifyInjection() {
        return this.exampleService.getExampleText();
    }

}
