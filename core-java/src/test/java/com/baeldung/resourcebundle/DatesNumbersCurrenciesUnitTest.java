package com.baeldung.resourcebundle;

import org.junit.Test;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class DatesNumbersCurrenciesUnitTest {

    @Test
    public void givenLocalDateAndLocaleUs_whenFormatDate_thenMonthJan() {
        LocalDate sampleDate = LocalDate.of(2018, 1, 1);
        Locale us = Locale.US;

        DateTimeFormatter usDTF = DateTimeFormatter.ofPattern("yyyy MMM dd")
            .withLocale(us);
        String formattedSampleDate = sampleDate.format(usDTF);

        assertEquals(formattedSampleDate, "2018 Jan 01");
    }

    @Test
    public void givenLocalDateAndLocalePlPl_whenFormatDate_thenMonthSty() {
        LocalDate sampleDate = LocalDate.of(2018, 1, 1);
        Locale pl = new Locale("pl", "PL");

        DateTimeFormatter plDTF = DateTimeFormatter.ofPattern("yyyy MMM dd")
            .withLocale(pl);
        String formattedSampleDate = sampleDate.format(plDTF);

        assertEquals(formattedSampleDate, "2018 sty 01");
    }

    @Test
    public void givenSampleNumberAndLocaleUs_whenFormatNumber_thenCommasAndPoint() {
        String usNumber = NumberFormat.getInstance(Locale.US)
            .format(16100200.345);

        assertEquals(usNumber, "16,100,200.345");
    }

    @Test
    public void givenSampleNumberAndLocaleUs_whenFormatNumber_thenSpacesAndComma() {
        String plNumber = NumberFormat.getInstance(new Locale("pl", "PL"))
            .format(16100200.345);

        assertEquals(plNumber, "16 100 200,345");
    }

    @Test
    public void givenSampleAmountAndLocaleUs_whenFormatCurrency_then() {
        String usAmount = NumberFormat.getCurrencyInstance(Locale.US)
            .format(16100200.345);

        assertEquals(usAmount, "$16,100,200.35");
    }

    @Test
    public void givenSampleAmountAndLocaleUs_whenFormatCurrency_thenSpacesAndCommaAndZl() {
        String plAmount = NumberFormat.getCurrencyInstance(new Locale("pl", "PL"))
            .format(16100200.345);

        assertEquals(plAmount, "16 100 200,35 zł");
    }
}
