package com.baeldung.reversenumber;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReverseNumberTest {

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
        ReverseNumber.reverseNumberRec(ORIGINAL_NUMBER);
        Assertions.assertThat(ReverseNumber.recursiveReversedNumber).isEqualTo(REVERSED_NUMBER);
    }
}