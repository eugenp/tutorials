package com.baeldung.spring_groovy_config;

import org.springframework.stereotype.Component;

import com.baeldung.spring_groovy_config.GroovyClass;

@Component
public class ClassWithRef {
    private GroovyClass myClass = null;
    
    public void setMyClass(GroovyClass myClass){
        this.myClass = myClass;
    }
    
    public GroovyClass getMyClass(){
        return this.myClass;
    }
}
