package com.baeldung.java8.lambda.exceptions;

import java.util.function.Predicate;

@FunctionalInterface
interface Validator<T> extends Predicate<T> {

    @Override
    boolean test(T t);
}