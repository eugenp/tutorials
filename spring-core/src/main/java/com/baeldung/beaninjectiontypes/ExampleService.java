package com.baeldung.beaninjectiontypes;

import org.springframework.stereotype.Component;

@Component
public class ExampleService {

    public String getExampleText() {
        return "Example";
    }
}
