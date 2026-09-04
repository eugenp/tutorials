package com.baeldung.java8.lambda.exceptions;

class RecordStream {

    static <E> boolean validate(E record, Validator<? super E> validator) {
        return validator.test(record);
    }
}