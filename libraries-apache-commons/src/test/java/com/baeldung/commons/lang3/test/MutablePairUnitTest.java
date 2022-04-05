package com.baeldung.commons.lang3.test;

import org.apache.commons.lang3.tuple.MutablePair;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class MutablePairUnitTest {
    
    private static MutablePair<String, String> mutablePair;
    
    @BeforeClass
    public static void setUpMutablePairInstance() {
        mutablePair = new MutablePair<>("leftElement", "rightElement");
    }
    
    @Test
    public void givenMutablePairInstance_whenCalledgetLeft_thenCorrect() {
        assertThat(mutablePair.getLeft()).isEqualTo("leftElement");
    }
    
    @Test
    public void givenMutablePairInstance_whenCalledgetRight_thenCorrect() {
        assertThat(mutablePair.getRight()).isEqualTo("rightElement");
    }
    
    @Test
    public void givenMutablePairInstance_whenCalledsetLeft_thenCorrect() {
        mutablePair.setLeft("newLeftElement");
        assertThat(mutablePair.getLeft()).isEqualTo("newLeftElement");
    }
}
