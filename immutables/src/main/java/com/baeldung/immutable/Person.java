package com.baeldung.immutable;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Person {
    abstract String getName();
    abstract Integer getAge();
}
