package com.baeldung.commons.lang3.test;

import org.apache.commons.lang3.tuple.Triple;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class TripleUnitTest {
    
    private static Triple<String, String, String> triple;
    
    @BeforeClass
    public static void setUpTripleInstance() {
        triple = Triple.of("leftElement", "middleElement", "rightElement");
    }
    
    @Test
    public void givenTripleInstance_whenCalledgetLeft_thenCorrect() {
        assertThat(triple.getLeft()).isEqualTo("leftElement");
    }
    
    @Test
    public void givenTripleInstance_whenCalledgetMiddle_thenCorrect() {
        assertThat(triple.getMiddle()).isEqualTo("middleElement");
    }
    
    @Test
    public void givenTripleInstance_whenCalledgetRight_thenCorrect() {
        assertThat(triple.getRight()).isEqualTo("rightElement");
    }
}
