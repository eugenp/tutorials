package com.baeldung.testng.conditionalskip;

import org.testng.annotations.Test;

public class SkipAnnotationTransformerUnitTest {
    @Test
    public void givenSkipAnnotation_whenUsingIAnnotationTransformer_thenSkipTest() {
        System.out.println("Dummy Test!");
    }

    @Test
    public void givenSkipAnnotation_whenUsingIAnnotationTransformer_thenSkipTest2() {
        System.out.println("Dummy Test 2!");
    }
}
