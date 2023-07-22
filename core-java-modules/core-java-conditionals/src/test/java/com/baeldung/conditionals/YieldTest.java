package com.baeldung.conditionals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class YieldTest {

    @Test
    void testAnalyze() {
        Assertions.assertEquals("Got a 1", YieldExamples.analyze(YieldExamples.Number.ONE));
    }

    @Test
    void testAnalyzeNumberArrow() {
        Assertions.assertEquals("Got a 2", YieldExamples.analyzeNumberArrow(YieldExamples.Number.TWO));
    }

    @Test
    void testAnalyzeNumberColon() {
        Assertions.assertEquals("More than 2", YieldExamples.analyzeNumberColon(YieldExamples.Number.THREE));
    }

}
