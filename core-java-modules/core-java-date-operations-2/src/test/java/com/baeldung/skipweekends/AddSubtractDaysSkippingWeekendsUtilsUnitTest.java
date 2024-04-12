package com.baeldung.skipweekends;

import static org.junit.Assert.assertEquals;

import com.baeldung.skipweekends.AddSubtractDaysSkippingWeekendsUtils;
import org.junit.Test;

import java.time.LocalDate;

public class AddSubtractDaysSkippingWeekendsUtilsUnitTest {

    @Test
    public void givenLocalDateAndDaysToAdd_thenAddDaysSkippingWeekends() {
        LocalDate initialDate = LocalDate.of(2019, 11, 7);
        LocalDate expectedDate = LocalDate.of(2019, 11, 13);
        LocalDate result = AddSubtractDaysSkippingWeekendsUtils.addDaysSkippingWeekends(initialDate, 4);
        assertEquals(expectedDate, result);
    }

    @Test
    public void givenLocalDateAndDaysToSubtract_thenSubtractDaysSkippingWeekends() {
        LocalDate initialDate = LocalDate.of(2019, 11, 7);
        LocalDate expectedDate = LocalDate.of(2019, 11, 1);
        LocalDate result = AddSubtractDaysSkippingWeekendsUtils.subtractDaysSkippingWeekends(initialDate, 4);
        assertEquals(expectedDate, result);
    }

}
