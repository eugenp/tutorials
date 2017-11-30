package com.baeldung.di.types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConstructorBasedInjectionDemo {

    private Environment environment;
    
    
    @Autowired
    public ConstructorBasedInjectionDemo(Environment env){
        this.environment=env;
    }
    
    public String getUserHome(){
        return environment.getProperty("user.home");
    }
    
}
