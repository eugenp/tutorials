package com.baeldung.beaninjectiontypes.xmlconfigexample;

public class SimpleBeanConstructorInjection {

    private SimpleDependency simpleDependency;

    SimpleBeanConstructorInjection(SimpleDependency simpleDependency) {
        this.simpleDependency = simpleDependency;
    }

    public void doSomething() {
        System.out.println("I'm a Simple Bean. My dependency is wired using constructor injection - I'm doing something!");
        simpleDependency.doSomethingElse();
    }

}
