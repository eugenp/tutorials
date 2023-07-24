package com.baeldung.conditionals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class YieldTest {

    @Test
    void testAnalyze() {
        assertEquals("Got a 1", YieldExamples.analyze(YieldExamples.Number.ONE));
    }

    @Test
    void testAnalyzeNumberArrow() {
        assertEquals("Got a 2", YieldExamples.analyzeNumberArrow(YieldExamples.Number.TWO));
    }

    @Test
    void testAnalyzeNumberColon() {
        assertEquals("More than 2", YieldExamples.analyzeNumberColon(YieldExamples.Number.THREE));
    }

}
