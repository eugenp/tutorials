package com.baeldung.dependency;

import org.springframework.stereotype.Component;

@Component
public class YetAnotherArbitraryDependency extends ArbitraryDependency {

    private final String label = "Yet Another Arbitrary Dependency";

    public String toString() {
        return label;
    }
}
