package com.baeldung.migration.junit4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class AssertionsExampleUnitTest {
    @Test
    @Ignore
    public void shouldFailBecauseTheNumbersAreNotEqualld() {
        assertEquals("Numbers are not equal!", 2, 3);
    }

    @Test
    public void shouldAssertAllTheGroup() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals("List is not incremental", list.get(0)
            .intValue(), 1);
        assertEquals("List is not incremental", list.get(1)
            .intValue(), 2);
        assertEquals("List is not incremental", list.get(2)
            .intValue(), 3);
    }
}
