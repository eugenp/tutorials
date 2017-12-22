package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExampleWithConstructorInjection {

    private ExampleService exampleService;

    @Autowired
    public ExampleWithConstructorInjection(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public String verifyInjection() {
        return this.exampleService.getExampleText();
    }
}
