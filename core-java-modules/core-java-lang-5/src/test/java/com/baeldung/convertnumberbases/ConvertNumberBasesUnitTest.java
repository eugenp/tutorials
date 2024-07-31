package com.baeldung.convertnumberbases;

import static com.baeldung.convertnumberbases.ConvertNumberBases.convertFromDecimalToBaseX;
import static com.baeldung.convertnumberbases.ConvertNumberBases.convertNumberToNewBase;
import static com.baeldung.convertnumberbases.ConvertNumberBases.convertNumberToNewBaseCustom;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConvertNumberBasesUnitTest {

    @Test
    void whenConvertingBase10NumberToBase5_ThenResultShouldBeDigitsInBase5() {
        assertEquals(convertNumberToNewBase("89", 10, 5), "324");
    }
    @Test
    void whenConvertingBase2NumberToBase8_ThenResultShouldBeDigitsInBase8() {
        assertEquals(convertNumberToNewBaseCustom("11001000", 2, 8), "310");
    }

    @Test
    void whenInputIsOutOfRange_thenIllegalArgumentExceptionIsThrown() {
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
            convertFromDecimalToBaseX(100, 12);
        });
        String expectedMessage = "New base must be from 2 - 10 or 16";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}