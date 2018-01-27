package com.baeldung.beaninjectiontypes.xmlconfigexample;

public class SimpleBeanSetterInjection {

    private SimpleDependency simpleDependency;

    public void setSimpleDependency(SimpleDependency simpleDependency) {
        this.simpleDependency = simpleDependency;
    }

    public void doSomething() {
        System.out.println("I'm a Simple Bean. My dependency is wired using setter injection - I'm doing something!");
        simpleDependency.doSomethingElse();
    }

}
