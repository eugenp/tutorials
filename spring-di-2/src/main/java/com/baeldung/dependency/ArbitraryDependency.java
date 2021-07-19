package com.baeldung.dependency;

import org.springframework.stereotype.Component;

@Component(value = "autowiredFieldDependency")
public class ArbitraryDependency {

    private final String label = "Arbitrary Dependency";

    public String toString() {
        return label;
    }
}
