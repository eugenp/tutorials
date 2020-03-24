package com.baeldung.immutable.parameter;


import org.immutables.value.Value;

@Value.Immutable
public abstract class Person {

    @Value.Parameter
    abstract String getName();

    @Value.Parameter
    abstract Integer getAge();
}