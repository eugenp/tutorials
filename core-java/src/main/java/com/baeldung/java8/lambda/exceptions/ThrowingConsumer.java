package com.baeldung.java8.lambda.exceptions;

@FunctionalInterface
interface ThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;

}