package com.baeldung.algorithms.uniquedigit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniqueDigitCounterUnitTest {

    @Test
    public void givenNotNegativeNumber_whenCountUniqueDigits_thenCorrectCount() {
        assertEquals(3, UniqueDigitCounter.countWithSet(122333));
        assertEquals(1, UniqueDigitCounter.countWithSet(0));
        assertEquals(2, UniqueDigitCounter.countWithSet(101));

        assertEquals(3, UniqueDigitCounter.countWithBitManipulation(122333));
        assertEquals(1, UniqueDigitCounter.countWithBitManipulation(0));
        assertEquals(2, UniqueDigitCounter.countWithBitManipulation(101));

        assertEquals(3, UniqueDigitCounter.countWithStreamApi(122333));
        assertEquals(1, UniqueDigitCounter.countWithStreamApi(0));
        assertEquals(2, UniqueDigitCounter.countWithStreamApi(101));
    }

    @Test
    public void givenNegativeNumber_whenCountUniqueDigits_thenCorrectCount() {
        assertEquals(3, UniqueDigitCounter.countWithSet(-122333));
        assertEquals(3, UniqueDigitCounter.countWithBitManipulation(-122333));
        assertEquals(3, UniqueDigitCounter.countWithStreamApi(-122333));
    }

    @Test
    public void givenLargeNumber_whenCountUniqueDigits_thenCorrectCount() {
        assertEquals(2, UniqueDigitCounter.countWithSet(1000000000));
        assertEquals(2, UniqueDigitCounter.countWithBitManipulation(1000000000));
        assertEquals(2, UniqueDigitCounter.countWithStreamApi(1000000000));
    }

}
