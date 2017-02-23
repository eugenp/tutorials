package com.baeldung.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterDependencyInjection {

    private TestDependency dependency;

    // Inject the dependency through setter method.
    @Autowired
    public void setDependency(TestDependency dependency) {
        this.dependency = dependency;
    }

    public int add(int a, int b) {
        // Use the dependency here.
        int value = dependency.addNumbers(a, b);
        return value;
    }
}