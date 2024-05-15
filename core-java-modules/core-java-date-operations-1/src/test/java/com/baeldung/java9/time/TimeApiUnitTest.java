package com.baeldung.java9.time;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeApiUnitTest {

    @Test
    public void givenGetDatesBetweenWithUsingJava7_WhenStartEndDate_thenDatesList() {
        Date startDate = Calendar.getInstance().getTime();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.DATE, 2);
        Date endDate = endCalendar.getTime();

        List<Date> dates = TimeApi.getDatesBetweenUsingJava7(startDate, endDate);

        assertThat(dates).hasSize(2);

        Calendar calendar = Calendar.getInstance();
        Date expectedDate1 = calendar.getTime();
        assertThat(dates.get(0)).isInSameDayAs(expectedDate1);
        assertThatTimeFieldsAreZero(dates.get(0));

        calendar.add(Calendar.DATE, 1);
        Date expectedDate2 = calendar.getTime();
        assertThat(dates.get(1)).isInSameDayAs(expectedDate2);
        assertThatTimeFieldsAreZero(dates.get(1));
    }

    @Test
    public void givenGetDatesBetweenWithUsingJava8_WhenStartEndDate_thenDatesList() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);

        List<LocalDate> dates = TimeApi.getDatesBetweenUsingJava8(startDate, endDate);

        assertThat(dates).containsExactly(LocalDate.now(), LocalDate.now().plusDays(1));
    }

    @Test
    public void givenGetDatesBetweenWithUsingJava9_WhenStartEndDate_thenDatesList() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);

        List<LocalDate> dates = TimeApi.getDatesBetweenUsingJava9(startDate, endDate);

        assertThat(dates).containsExactly(LocalDate.now(), LocalDate.now().plusDays(1));
    }

    private static void assertThatTimeFieldsAreZero(Date date) {
        assertThat(date).hasHourOfDay(0);
        assertThat(date).hasMinute(0);
        assertThat(date).hasSecond(0);
        assertThat(date).hasMillisecond(0);
    }

}
