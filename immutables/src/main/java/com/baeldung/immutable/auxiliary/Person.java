package com.baeldung.immutable.auxiliary;


import org.immutables.value.Value;

@Value.Immutable
public abstract class Person {
    abstract String getName();
    abstract Integer getAge();

    @Value.Auxiliary
    abstract String getAuxiliaryField();
}