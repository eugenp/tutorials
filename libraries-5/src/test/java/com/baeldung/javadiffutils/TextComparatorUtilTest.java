package com.baeldung.javadiffutils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TextComparatorUtilTest {
    @Test
    public void givenDifferentLines_whenCompared_thenDetectsChanges() {
        var original = List.of("A", "B", "C");
        var revised = List.of("A", "B", "D");

        var patch = TextComparatorUtil.compare(original, revised);

        assertEquals(1, patch.getDeltas().size());
        assertEquals("C", patch.getDeltas().get(0).getSource().getLines().get(0));
        assertEquals("D", patch.getDeltas().get(0).getTarget().getLines().get(0));
    }
}
