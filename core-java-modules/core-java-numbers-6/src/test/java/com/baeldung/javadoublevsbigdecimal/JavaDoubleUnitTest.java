package com.baeldung.javadoublevsbigdecimal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JavaDoubleUnitTest {

    @Test
    public void givenDoubleLiteral_whenAssigningToDoubleVariable_thenValueIsNotExactlyEqual() {
        double doubleValue = 0.1;
        double epsilon = 0.0000000000000001;
        assertEquals(0.1, doubleValue, epsilon);
    }
}
