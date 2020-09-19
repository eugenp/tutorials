package com.baeldung.assume;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

import org.junit.Test;

public class ConditionallyIgnoreTestsUnitTest {


    @Test
    public void whenAssumeThatCodeVersionIsNot2_thenIgnore() {
        final int codeVersion = 1;
        assumeThat(codeVersion, is(2));

        assertEquals("hello", "HELLO".toLowerCase());
    }

    @Test
    public void whenAssumeTrueOnCondition_thenIgnore() {
        final int codeVersion = 1;
        assumeTrue(isCodeVersion2(codeVersion));

        assertEquals("hello", "HELLO".toLowerCase());
    }

    @Test
    public void whenAssumeFalseOnCondition_thenIgnore() {
        final int codeVersion = 2;
        assumeFalse(isCodeVersion2(codeVersion));

        assertEquals("hello", "HELLO".toLowerCase());
    }

    private boolean isCodeVersion2(final int codeVersion) {
        return codeVersion == 2;
    }
}
