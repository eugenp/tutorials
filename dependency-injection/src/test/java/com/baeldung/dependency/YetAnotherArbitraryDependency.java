package com.baeldung.dependency;

public class YetAnotherArbitraryDependency extends ArbitraryDependency {

    private final String label = "Yet Another Arbitrary Dependency";

    public String toString() {
        return label;
    }
}
