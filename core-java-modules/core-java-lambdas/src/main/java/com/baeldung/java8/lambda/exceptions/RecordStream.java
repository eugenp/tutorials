package com.baeldung.java8.lambda.exceptions;

class RecordStream<E> {

    static <T> RecordStream<T> create() {
        return new RecordStream<>();
    }

    RecordStream<E> validate(Validator<? super E> validator) {
        return this;
    }

}