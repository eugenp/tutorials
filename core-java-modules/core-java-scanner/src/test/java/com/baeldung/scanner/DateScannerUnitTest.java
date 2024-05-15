package com.baeldung.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class DateScannerUnitTest {

    @Test
    void whenScanToLocalDate_ThenCorrectLocalDate() {
        String dateString = "2018-09-09";
        assertEquals(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")), new DateScanner().scanToLocalDate(dateString));
    }

    @Test
    void whenScanToDate_ThenCorrectDate() throws ParseException {
        String dateString = "2018-09-09";
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse(dateString), new DateScanner().scanToDate(dateString));
    }

}
