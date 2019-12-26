/**
 * 
 */
package com.baeldung.datetolocaldate;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.baeldung.datetolocaldate.DateToLocalDateTimeConverter;

/**
 * JUnits for {@link DateToLocalDateTimeConverter} class.
 * 
 * @author abialas
 *
 */
public class DateToLocalDateTimeConverterUnitTest {

    @Test
    public void shouldReturn10thNovember2010time8hour20minWhenConvertViaInstant() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 10, 10, 8, 20);
        Date dateToConvert = calendar.getTime();

        // when
        LocalDateTime localDateTime = DateToLocalDateTimeConverter.convertToLocalDateTimeViaInstant(dateToConvert);

        // then
        assertEquals(2010, localDateTime.get(ChronoField.YEAR));
        assertEquals(11, localDateTime.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(10, localDateTime.get(ChronoField.DAY_OF_MONTH));
        assertEquals(8, localDateTime.get(ChronoField.HOUR_OF_DAY));
        assertEquals(20, localDateTime.get(ChronoField.MINUTE_OF_HOUR));
    }

    @Test
    public void shouldReturn10thNovember2010time8hour20minWhenConvertViaMiliseconds() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 10, 10, 8, 20);
        Date dateToConvert = calendar.getTime();

        // when
        LocalDateTime localDateTime = DateToLocalDateTimeConverter.convertToLocalDateTimeViaMilisecond(dateToConvert);

        // then
        assertEquals(2010, localDateTime.get(ChronoField.YEAR));
        assertEquals(11, localDateTime.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(10, localDateTime.get(ChronoField.DAY_OF_MONTH));
        assertEquals(8, localDateTime.get(ChronoField.HOUR_OF_DAY));
        assertEquals(20, localDateTime.get(ChronoField.MINUTE_OF_HOUR));
    }

    @Test
    public void shouldReturn10thNovember2010time8hour20minWhenConvertViaSqlTimestamp() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 10, 10, 8, 20);
        Date dateToConvert = calendar.getTime();

        // when
        LocalDateTime localDateTime = DateToLocalDateTimeConverter.convertToLocalDateTimeViaSqlTimestamp(dateToConvert);

        // then
        assertEquals(2010, localDateTime.get(ChronoField.YEAR));
        assertEquals(11, localDateTime.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(10, localDateTime.get(ChronoField.DAY_OF_MONTH));
        assertEquals(8, localDateTime.get(ChronoField.HOUR_OF_DAY));
        assertEquals(20, localDateTime.get(ChronoField.MINUTE_OF_HOUR));
    }

    @Test
    public void shouldReturn10thNovember2010time8hour20minWhenConvertToLocalDateTime() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 10, 10, 8, 20);
        Date dateToConvert = calendar.getTime();

        // when
        LocalDateTime localDateTime = DateToLocalDateTimeConverter.convertToLocalDateTime(dateToConvert);

        // then
        assertEquals(2010, localDateTime.get(ChronoField.YEAR));
        assertEquals(11, localDateTime.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(10, localDateTime.get(ChronoField.DAY_OF_MONTH));
        assertEquals(8, localDateTime.get(ChronoField.HOUR_OF_DAY));
        assertEquals(20, localDateTime.get(ChronoField.MINUTE_OF_HOUR));
    }

}
