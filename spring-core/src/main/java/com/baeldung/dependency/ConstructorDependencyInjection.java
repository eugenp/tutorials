package com.baeldung.dependency;

import org.springframework.beans.factory.annotation.Autowired;

public class ConstructorDependencyInjection {
    private TestDependency dependency;

    // Inject the dependency through constructor.
    @Autowired
    public ConstructorDependencyInjection(TestDependency dependency) {
        this.dependency = dependency;
    }

    public int add(int a, int b) {
        // Use the dependency here.
        int value = dependency.addNumbers(a, b);
        return value;
    }
}
