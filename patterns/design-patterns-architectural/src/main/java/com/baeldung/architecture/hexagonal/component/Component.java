package com.baeldung.architecture.hexagonal.component;

import java.util.Arrays;
import java.util.List;

public class Component {
    private List<DependencyAdapter> dependencyAdapters;

    public Component(DependencyAdapter... dependencyAdapters) {
        this.dependencyAdapters = Arrays.asList(dependencyAdapters);
    }

    public void accept(String string) {
        dependencyAdapters.forEach(d -> d.adaptAndCallDependency(string));
    }
}
