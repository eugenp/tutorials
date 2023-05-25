package com.baeldung.reversenumber;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReverseNumberUnitTest {

    private static final int ORIGINAL_NUMBER = 123456789;
    private static final int REVERSED_NUMBER = 987654321;

    @Test
    void whenReverseNumberWhileLoop_thenOriginalEqualToReverse() {
        Assertions.assertThat(ReverseNumber.reverseNumberWhileLoop(ORIGINAL_NUMBER)).isEqualTo(REVERSED_NUMBER);
    }

    @Test
    void whenReverseNumberForLoop_thenOriginalEqualToReverse() {
        Assertions.assertThat(ReverseNumber.reverseNumberForLoop(ORIGINAL_NUMBER)).isEqualTo(REVERSED_NUMBER);
    }

    @Test
    void whenReverseNumberRec_thenOriginalEqualToReverse() {
        Assertions.assertThat(ReverseNumber.reverseNumberRecWrapper(ORIGINAL_NUMBER)).isEqualTo(REVERSED_NUMBER);
    }

    @Test
    void whenReverseNegativeNumber_thenNumberShouldReverse() {
        Assertions.assertThat(ReverseNumber.reverseNumberWhileLoop(ORIGINAL_NUMBER * -1)).isEqualTo(REVERSED_NUMBER * -1);
        Assertions.assertThat(ReverseNumber.reverseNumberForLoop(ORIGINAL_NUMBER * -1)).isEqualTo(REVERSED_NUMBER * -1);
        Assertions.assertThat(ReverseNumber.reverseNumberRecWrapper(ORIGINAL_NUMBER * -1)).isEqualTo(REVERSED_NUMBER * -1);
    }
}