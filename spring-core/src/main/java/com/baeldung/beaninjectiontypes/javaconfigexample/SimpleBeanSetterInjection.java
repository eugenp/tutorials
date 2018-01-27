package com.baeldung.beaninjectiontypes.javaconfigexample;

public class SimpleBeanSetterInjection {

    private SimpleDependency simpleDependency;

    public void doSomething() {
        System.out.println("I'm a Simple Bean. My dependency is wired using setter injection - I'm doing something!");
        simpleDependency.doSomethingElse();
    }

    public void setSimpleDependency(SimpleDependency simpleDependency) {
        this.simpleDependency = simpleDependency;
    }

}
