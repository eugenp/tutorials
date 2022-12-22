package com.baeldung.autowiring.service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * The bean corresponding to this class is defined in MyServiceConfiguration
 * Alternatively, you could choose to decorate this class with @Component or @Service
 */
public class MyService {
    
    @Autowired
    MyComponent myComponent;
    
    public String serve() {
        myComponent.doWork();
        return "success";
    }

}
