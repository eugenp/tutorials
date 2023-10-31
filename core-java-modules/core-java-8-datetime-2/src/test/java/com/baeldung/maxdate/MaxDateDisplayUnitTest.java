package com.baeldung.maxdate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaxDateDisplayUnitTest {

    @Test
    public void whenGetMaxDate_thenCorrectResult() {
        MaxDateDisplay display = new MaxDateDisplay();
        String result = display.getMaxDateValue();
        assertEquals(result, "The maximum date value in Java is: 292278994-08-17");
    }
}