package com.baeldung.datetime;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDate;

public class AddSubtractDaysSkippingWeekendsUtilsUnitTest {

    @Test
    public void givenLocalDateAndDaysToAdd_thenAddDaysSkippingWeekendsUsingIterativeMethod() {
        LocalDate initialDate = LocalDate.of(2019, 11, 7);
        LocalDate expectedDate = LocalDate.of(2019, 11, 13);
        LocalDate result = AddSubtractDaysSkippingWeekendsUtils.addSubtractDaysSkippingWeekendsIterativeMethod(initialDate, 4);
        assertEquals(expectedDate, result);
    }

    @Test
    public void givenLocalDateAndDaysToAdd_thenSubtractDaysSkippingWeekendsUsingIterativeMethod() {
        LocalDate initialDate = LocalDate.of(2019, 11, 7);
        LocalDate expectedDate = LocalDate.of(2019, 11, 1);
        LocalDate result = AddSubtractDaysSkippingWeekendsUtils.addSubtractDaysSkippingWeekendsIterativeMethod(initialDate, -4);
        assertEquals(expectedDate, result);
    }

}
