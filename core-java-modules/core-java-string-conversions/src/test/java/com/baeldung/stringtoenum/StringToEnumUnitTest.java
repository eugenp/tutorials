package com.baeldung.stringtoenum;

import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

public class StringToEnumUnitTest {
    
    @Test
    public void whenConvertedIntoEnum_thenGetsConvertedCorrectly() {
        String pizzaEnumValue = "READY";
        PizzaStatusEnum pizzaStatusEnum = PizzaStatusEnum.valueOf(pizzaEnumValue);
        assertTrue(pizzaStatusEnum == PizzaStatusEnum.READY);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void whenConvertedIntoEnum_thenThrowsException() {
        String pizzaEnumValue = "rEAdY";
        PizzaStatusEnum pizzaStatusEnum = PizzaStatusEnum.valueOf(pizzaEnumValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidEnumValueContentWiseAsString_whenConvertedIntoEnum_thenThrowsException() {
        String pizzaEnumValue = "invalid";
        PizzaStatusEnum pizzaStatusEnum = PizzaStatusEnum.valueOf(pizzaEnumValue);
    }
}