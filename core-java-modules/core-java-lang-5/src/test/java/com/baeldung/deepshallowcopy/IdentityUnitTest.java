package com.baeldung.deepshallowcopy;

import static org.junit.Assert.assertSame;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class IdentityUnitTest {

    void assumeStandardIntegerCache() {
        Integer max = Integer.getInteger("java.lang.Integer.IntegerCache.high");

        assumeTrue(max == null || max <= 127);
    }

    @Test
    void givenSmallValues_thenBoxingWillNotAllocateObjects() {
        assumeStandardIntegerCache();

        Integer a = 42;
        Integer b = 42;

        assertEquals(a, b);
        assertSame(a, b);

        a = 128;
        b = 128;

        assertEquals(a, b);
        assertNotSame(a, b);
    }

}