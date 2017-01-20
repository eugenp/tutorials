package com.baeldung.constructorvsfielddi.components;

import com.baeldung.constructorvsfielddi.dependencies.AnotherDependency;
import com.baeldung.constructorvsfielddi.dependencies.SimpleDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentConstructorInjection {

    private AnotherDependency anotherDependency;
    private SimpleDependency dependency;

    @Autowired
    public ComponentConstructorInjection(AnotherDependency anotherDependency, SimpleDependency dependency) {
        this.anotherDependency = anotherDependency;
        this.dependency = dependency;
    }

    public void doSomething() {
        dependency.doSimple();
        anotherDependency.doAnother();
    }

}
