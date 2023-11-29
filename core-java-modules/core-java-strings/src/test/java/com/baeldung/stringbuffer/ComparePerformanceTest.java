package com.baeldung.stringbuffer;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ComparePerformanceTest {

    ComparePerformance cp = new ComparePerformance();

    @Test
    public void whenStringConcatenated_thenResultAsExpected() {
        assertThat(cp.benchmarkStringConcatenation()).isEqualTo("springframework");
    }

    @Test
    public void whenStringBufferConcatenated_thenResultAsExpected() {
        StringBuffer stringBuffer = new StringBuffer("springframework");
        assertThat(cp.benchmarkStringBufferConcatenation()).isEqualToIgnoringCase(stringBuffer);
    }

    @Test
    public void whenStringReplaced_thenResultAsExpected() {
        assertThat(cp.benchmarkStringReplacement()).isEqualTo("java-framework");
    }

    @Test
    public void whenStringBufferReplaced_thenResultAsExpected() {
        StringBuffer stringBuffer = new StringBuffer("java-framework");
        assertThat(cp.benchmarkStringBufferReplacement()).isEqualToIgnoringCase(stringBuffer);
    }
}
