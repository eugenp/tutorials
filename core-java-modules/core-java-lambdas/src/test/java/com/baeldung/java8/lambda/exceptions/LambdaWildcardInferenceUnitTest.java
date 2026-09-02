package com.baeldung.java8.lambda.exceptions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LambdaWildcardInferenceUnitTest {

    @Test
    void whenImplicitlyTypedLambdaPassedToWildcardParameter_thenSuccess() {
        Validator<? super Boolean> validator = b -> b;
        RecordStream<Boolean> stream = RecordStream.<Boolean> create()
            .validate(b -> b);

        assertTrue(validator.test(true));
        assertFalse(validator.test(false));
    }
}