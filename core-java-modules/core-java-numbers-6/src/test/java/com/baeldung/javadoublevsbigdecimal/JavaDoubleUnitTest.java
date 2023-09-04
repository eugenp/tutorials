package com.baeldung.javadoublevsbigdecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JavaDoubleUnitTest {
    @Test
    public void givenDoubleLiteral_whenAssigningToDoubleVariable_thenValueIsNotExactlyEqual() {
        double d = 0.1;
        double epsilon = 0.0000000000000001;
        assertNotEquals(0.1, d);
        assertEquals(0.1, d, epsilon);
    }
}
