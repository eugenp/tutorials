package com.baeldung.monkey.patching.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyConverterUnitTest {

    @Test
    void whenMoneyConverter_thenResultIsCorrect() {
        MoneyConverterImpl moneyConverter = new MoneyConverterImpl();

        double result = moneyConverter.convertEURtoUSD(10);

        assertEquals(11, result);
    }
}