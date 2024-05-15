package com.baeldung.date;

import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class AgeCalculatorUnitTest {
    AgeCalculator ageCalculator = new AgeCalculator();

    @Test
    public void givenLocalDate_whenCalculateAge_thenOk() {
        assertEquals(10, ageCalculator.calculateAge(LocalDate.of(2008, 5, 20), LocalDate.of(2018, 9, 20)));
    }

    @Test
    public void givenJodaTime_whenCalculateAge_thenOk() {
        assertEquals(10, ageCalculator.calculateAgeWithJodaTime(new org.joda.time.LocalDate(2008, 5, 20), new org.joda.time.LocalDate(2018, 9, 20)));
    }

    @Test
    public void givenDate_whenCalculateAge_thenOk() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date birthDate = sdf.parse("2008-05-20");
        Date currentDate = sdf.parse("2018-09-20");
        assertEquals(10, ageCalculator.calculateAgeWithJava7(birthDate, currentDate));
    }

}