package com.baeldung.beaninjectiontypes.autowiredexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleAutowiredBean {

    @Autowired
    private SimpleAutowiredDependency simpleAutowiredDependency;

    public void doSomething() {
        System.out.println("I'm a Simple Bean. I'm doing something!");
        simpleAutowiredDependency.doSomethingElse();
    }
}
