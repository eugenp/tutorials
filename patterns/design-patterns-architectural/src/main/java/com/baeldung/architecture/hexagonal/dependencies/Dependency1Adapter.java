package com.baeldung.architecture.hexagonal.dependencies;

import com.baeldung.architecture.hexagonal.component.DependencyAdapter;

public class Dependency1Adapter implements DependencyAdapter {
    private final Dependency1 dependency1;

    public Dependency1Adapter(Dependency1 dependency1) {
        this.dependency1 = dependency1;
    }

    @Override
    public void adaptAndCallDependency(String string) {
        dependency1.accept(string.length());
    }
}
