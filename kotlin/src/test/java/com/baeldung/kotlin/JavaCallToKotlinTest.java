package com.baeldung.kotlin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavaCallToKotlinTest {
    @Test
    public void givenKotlinClass_whenCallFromJava_shouldProduceResults() {
        //when
        int res = new MathematicsOperations().addTwoNumbers(2, 4);

        //then
        assertEquals(6, res);

    }
}
