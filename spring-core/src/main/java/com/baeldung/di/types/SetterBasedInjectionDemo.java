package com.baeldung.di.types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SetterBasedInjectionDemo {

    @Autowired private Environment environment;
    
    public SetterBasedInjectionDemo(){
        
    }
    
    public String getUserHome(){
        return environment.getProperty("user.home");
    }
}
