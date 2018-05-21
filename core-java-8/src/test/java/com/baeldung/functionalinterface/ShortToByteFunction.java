package com.baeldung.functionalinterface;

@FunctionalInterface
public interface ShortToByteFunction {

    byte applyAsByte(short s);

}
