package com.baeldung.beaninjectiontypes.autowiredexample;

import org.springframework.stereotype.Component;

@Component
public class SimpleAutowiredDependency {

    public void doSomethingElse() {
        System.out.println("I'm a simple autowired dependency! I'm doing something!");
    }

}
