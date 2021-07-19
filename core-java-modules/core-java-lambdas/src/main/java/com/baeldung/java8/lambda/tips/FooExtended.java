package com.baeldung.java8.lambda.tips;


@FunctionalInterface
public interface FooExtended extends Baz, Bar {

    @Override
    default String defaultCommon() {
        return Bar.super.defaultCommon();
    }
}
