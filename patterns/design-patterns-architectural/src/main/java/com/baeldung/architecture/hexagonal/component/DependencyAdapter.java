package com.baeldung.architecture.hexagonal.component;

public interface DependencyAdapter {
    void adaptAndCallDependency(String string);
}
