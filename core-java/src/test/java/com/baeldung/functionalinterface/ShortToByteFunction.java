package com.baeldung.functionalinterface;

@FunctionalInterface
interface ShortToByteFunction {

    byte applyAsByte(short s);

}
