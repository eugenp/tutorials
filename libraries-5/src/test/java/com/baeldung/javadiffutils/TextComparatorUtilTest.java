package com.baeldung.javadiffutils.Utils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TextComparatorUtilTest {
    @Test
    public void givenDifferentLines_whenCompared_thenDetectsChanges() {
        var original = List.of("line1", "line2", "line3");
        var revised = List.of("line1", "line2", "line4");
        var patch = new TextComparatorUtil().compare(original, revised);
        assertEquals(1, patch.getDeltas().size());
        assertEquals("line3", patch.getDeltas().get(0).getSource().getLines().get(0));
        assertEquals("line4", patch.getDeltas().get(0).getTarget().getLines().get(0));
    }
}
