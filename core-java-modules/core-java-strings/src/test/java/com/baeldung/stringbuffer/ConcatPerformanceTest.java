package com.baeldung.stringbuffer;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ConcatPerformanceTest {

    ConcatPerformance cp = new ConcatPerformance();

    @Test
    public void whenConcatenated_thenStringBufferIsFasterThanString() {

        assertThat(cp.getStringConcatenationTime()).isGreaterThan(cp.getStringBufferConcatenationTime());
    }
}
