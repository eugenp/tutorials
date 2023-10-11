package com.baeldung.maxdate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaxDateDisplayTest {

    @Test
    public void whenGetMaxDate_thenCorrectResult() {
        MaxDateDisplay display = new MaxDateDisplay();
        String result = display.getMaxDateValue();
        assertTrue(result.startsWith("The maximum date value in Java is: 292278994-08-17"));
    }
}