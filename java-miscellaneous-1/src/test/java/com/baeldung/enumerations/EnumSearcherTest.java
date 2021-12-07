package com.baeldung.enumerations;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class EnumSearcherTest {

    @Test
    public void givenWeekdays_whenValidWeekdayNameProvided_validWeekdayIsFound() {
        Weekdays result = Weekdays.findByName("MONDAY");
        assertThat(result).isEqualTo(Weekdays.MONDAY);
    }

    @Test
    public void givenWeekdays_whenInvalidWeekdayNameProvided_nullIsReturned() {
        Weekdays result = Weekdays.findByName("MON");
        assertThat(result).isNull();
    }

    @Test
    public void givenWeekdays_whenValidWeekdayValueProvided_validWeekdayIsFound() {
        Weekdays result = Weekdays.findByValue("Monday");
        assertThat(result).isEqualTo(Weekdays.MONDAY);
    }

    @Test
    public void givenWeekdays_whenInvalidWeekdayValueProvided_nullIsReturned() {
        Weekdays result = Weekdays.findByValue("monday");
        assertThat(result).isNull();
    }

    @Test
    public void givenMonths_whenValidMonthNameProvided_optionalMonthIsReturned() {
        Optional<Months> result = Months.findByName("JANUARY");
        assertThat(result).isEqualTo(Optional.of(Months.JANUARY));
    }

    @Test
    public void givenMonths_whenInvalidMonthNameProvided_optionalEmptyIsReturned() {
        Optional<Months> result = Months.findByName("JAN");
        assertThat(result).isEmpty();
    }

    @Test
    public void givenMonths_whenValidMonthCodeProvided_optionalMonthIsReturned() {
        Optional<Months> result = Months.findByCode(1);
        assertThat(result).isEqualTo(Optional.of(Months.JANUARY));
    }

    @Test
    public void givenMonths_whenInvalidMonthCodeProvided_optionalEmptyIsReturned() {
        Optional<Months> result = Months.findByCode(0);
        assertThat(result).isEmpty();
    }

    @Test
    public void givenMonths_whenValidMonthValueProvided_monthIsReturned() {
        Months result = Months.findByValue("January");
        assertThat(result).isEqualTo(Months.JANUARY);
    }

    @Test
    public void givenMonths_whenInvalidMonthValueProvided_illegalArgExIsThrown() {
        assertThatIllegalArgumentException().isThrownBy(() -> Months.findByValue("Jan"));
    }
}
