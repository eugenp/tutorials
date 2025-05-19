package com.baeldung.javadiffutils.Utils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class UnifiedDiffGeneratorUtilTest {
    @Test
    public void givenModifiedText_whenUnifiedDiffGenerated_thenContainsExpectedChanges() {
        var original = List.of("x", "y", "z"); var revised = List.of("x", "y-modified", "z");
        var diff = new UnifiedDiffGeneratorUtil().generate(original, revised, "test.txt");
        assertTrue(diff.stream().anyMatch(line -> line.contains("-y")));
        assertTrue(diff.stream().anyMatch(line -> line.contains("+y-modified")));
    }
}
