package com.baeldung.java9.time;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TimeApiUnitTest {

    @Test
    public void givenGetDatesBetweenWithUsingJava7_WhenStartEndDate_thenDatesList() {
        Date startDate = Calendar.getInstance().getTime();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.DATE, 2);
        Date endDate = endCalendar.getTime();

        List<Date> dates = TimeApi.getDatesBetweenUsingJava7(startDate, endDate);
        assertEquals(dates.size(), 2);

        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        assertEquals(dates.get(0).getDay(), date1.getDay());
        assertEquals(dates.get(0).getMonth(), date1.getMonth());
        assertEquals(dates.get(0).getYear(), date1.getYear());

        calendar.add(Calendar.DATE, 1);
        Date date2 = calendar.getTime();
        assertEquals(dates.get(1).getDay(), date2.getDay());
        assertEquals(dates.get(1).getMonth(), date2.getMonth());
        assertEquals(dates.get(1).getYear(), date2.getYear());
    }

    @Test
    public void givenGetDatesBetweenWithUsingJava8_WhenStartEndDate_thenDatesList() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);

        List<LocalDate> dates = TimeApi.getDatesBetweenUsingJava8(startDate, endDate);
        assertEquals(dates.size(), 2);
        assertEquals(dates.get(0), LocalDate.now());
        assertEquals(dates.get(1), LocalDate.now().plusDays(1));
    }

    @Test
    public void givenGetDatesBetweenWithUsingJava9_WhenStartEndDate_thenDatesList() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);

        List<LocalDate> dates = TimeApi.getDatesBetweenUsingJava9(startDate, endDate);
        assertEquals(dates.size(), 2);
        assertEquals(dates.get(0), LocalDate.now());
        assertEquals(dates.get(1), LocalDate.now().plusDays(1));
    }

}
