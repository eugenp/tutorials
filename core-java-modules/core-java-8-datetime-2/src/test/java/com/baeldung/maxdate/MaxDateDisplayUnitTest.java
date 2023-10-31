package com.baeldung.maxdate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDateDisplayUnitTest {

    @Test
    public void whenGetMaxDate_thenCorrectResult() {
        MaxDateDisplay display = new MaxDateDisplay();
        String result = display.getMaxDateValue();
        assertEquals(result, "The maximum date value in Java is: 292278994-08-17 07:12:55.807");
    }
}