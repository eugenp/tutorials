package com.baeldung.constructorvsfielddi.components;

import com.baeldung.constructorvsfielddi.dependencies.AnotherDependency;
import com.baeldung.constructorvsfielddi.dependencies.SimpleDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentFieldInjection {

    @Autowired private AnotherDependency anotherDependency;

    @Autowired private SimpleDependency dependency;

    public void doSomething() {
        dependency.doSimple();
        anotherDependency.doAnother();
    }

}
