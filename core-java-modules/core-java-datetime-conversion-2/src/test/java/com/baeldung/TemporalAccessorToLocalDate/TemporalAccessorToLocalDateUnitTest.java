package com.baeldung.TemporalAccessorToLocalDate;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

import static org.junit.Assert.assertEquals;

public class TemporalAccessorToLocalDateUnitTest {
    String dateString = "2022-03-28";
    TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_LOCAL_DATE.parse(dateString);

    @Test
    public void givenTemporalAccessor_whenUsingLocalDateFrom_thenConvertToLocalDate() {
        LocalDate convertedDate = LocalDate.from(temporalAccessor);
        assertEquals(LocalDate.of(2022, 3, 28), convertedDate);
    }

    @Test
    public void givenTemporalAccessor_whenUsingTemporalQueries_thenConvertToLocalDate() {
        int year = temporalAccessor.query(TemporalQueries.localDate()).getYear();
        int month = temporalAccessor.query(TemporalQueries.localDate()).getMonthValue();
        int day = temporalAccessor.query(TemporalQueries.localDate()).getDayOfMonth();

        LocalDate convertedDate = LocalDate.of(year, month, day);
        assertEquals(LocalDate.of(2022, 3, 28), convertedDate);
    }
}