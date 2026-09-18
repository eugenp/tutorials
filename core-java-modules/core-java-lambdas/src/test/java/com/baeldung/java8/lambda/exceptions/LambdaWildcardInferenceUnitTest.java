package com.baeldung.java8.lambda.exceptions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LambdaWildcardInferenceUnitTest {

    @Test
    void whenImplicitlyTypedLambdaPassedToWildcardParameter_thenSuccess() {
        assertTrue(RecordStream.validate(Boolean.TRUE, b -> b));
        assertFalse(RecordStream.validate(Boolean.FALSE, b -> b));
    }
}