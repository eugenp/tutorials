/**
 * 
 */
package com.baeldung.java9.datetime;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.baeldung.java9.datetime.DateToLocalDateConverter;

/**
 * JUnits for {@link DateToLocalDateConverter} class.
 * 
 * @author abialas
 *
 */
public class DateToLocalDateConverterTest {

    @Test
    public void shouldReturn10thNovember2010WhenConvertToLocalDate() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 10, 10);
        Date dateToConvert = calendar.getTime();

        // when
        LocalDate localDateTime = DateToLocalDateConverter.convertToLocalDate(dateToConvert);

        // then
        assertEquals(2010, localDateTime.get(ChronoField.YEAR));
        assertEquals(11, localDateTime.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(10, localDateTime.get(ChronoField.DAY_OF_MONTH));
    }

}
