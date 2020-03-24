package com.baeldung.immutable.prehash;

import org.immutables.value.Value;

@Value.Immutable(prehash = true)
public abstract class Person {
    abstract String getName();
    abstract Integer getAge();
}
