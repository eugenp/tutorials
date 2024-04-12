package com.baeldung.jodadatetimetostandarddate;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JodaDateTimeToDateAndViceVersaUnitTest {
    @Test
    public void givenJodaDateTime_whenConvertingToJavaDate_thenConversionIsCorrect() {
        DateTime jodaDateTime = new DateTime();
        java.util.Date javaDate = jodaDateTime.toDate();
        assertEquals(jodaDateTime.getMillis(), javaDate.getTime());
    }

    @Test
    public void givenJavaDate_whenConvertingToJodaDateTime_thenConversionIsCorrect() {
        java.util.Date javaDate = new java.util.Date();
        DateTime jodaDateTime = new DateTime(javaDate);
        assertEquals(javaDate.getTime(), jodaDateTime.getMillis());
    }
}
