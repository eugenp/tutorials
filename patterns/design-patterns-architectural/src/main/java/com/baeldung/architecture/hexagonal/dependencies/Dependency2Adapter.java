package com.baeldung.architecture.hexagonal.dependencies;

import com.baeldung.architecture.hexagonal.component.DependencyAdapter;

public class Dependency2Adapter implements DependencyAdapter {
    private final Dependency2 dependency2;

    public Dependency2Adapter(Dependency2 dependency2) {
        this.dependency2 = dependency2;
    }

    @Override
    public void adaptAndCallDependency(String string) {
        dependency2.accept("(" + string + ")");
    }
}
