package com.baeldung.enums;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class EnumSearcherUnitTest {

    @Test
    public void givenWeekdays_whenValidDirectionNameProvided_directionIsFound() {
        Direction result = Direction.findByName("EAST");
        Assertions.assertThat(result).isEqualTo(Direction.EAST);
    }

    @Test
    public void givenWeekdays_whenValidDirectionNameLowerCaseProvided_directionIsFound() {
        Direction result = Direction.findByName("east");
        Assertions.assertThat(result).isEqualTo(Direction.EAST);
    }

    @Test
    public void givenWeekdays_whenInvalidDirectionNameProvided_nullIsReturned() {
        Direction result = Direction.findByName("E");
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void givenWeekdays_whenValidWeekdayNameProvided_weekdayIsFound() {
        Weekday result = Weekday.findByName("MONDAY");
        Assertions.assertThat(result).isEqualTo(Weekday.MONDAY);
    }

    @Test
    public void givenWeekdays_whenInvalidWeekdayNameProvided_nullIsReturned() {
        Weekday result = Weekday.findByName("MON");
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void givenWeekdays_whenValidWeekdayValueProvided_weekdayIsFound() {
        Weekday result = Weekday.findByValue("Monday");
        Assertions.assertThat(result).isEqualTo(Weekday.MONDAY);
    }

    @Test
    public void givenWeekdays_whenInvalidWeekdayValueProvided_nullIsReturned() {
        Weekday result = Weekday.findByValue("mon");
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void givenMonths_whenValidMonthNameProvided_optionalMonthIsReturned() {
        Optional<Month> result = Month.findByName("JANUARY");
        Assertions.assertThat(result).isEqualTo(Optional.of(Month.JANUARY));
    }

    @Test
    public void givenMonths_whenInvalidMonthNameProvided_optionalEmptyIsReturned() {
        Optional<Month> result = Month.findByName("JAN");
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void givenMonths_whenValidMonthCodeProvided_optionalMonthIsReturned() {
        Optional<Month> result = Month.findByCode(1);
        Assertions.assertThat(result).isEqualTo(Optional.of(Month.JANUARY));
    }

    @Test
    public void givenMonths_whenInvalidMonthCodeProvided_optionalEmptyIsReturned() {
        Optional<Month> result = Month.findByCode(0);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void givenMonths_whenValidMonthValueProvided_monthIsReturned() {
        Month result = Month.findByValue("January");
        Assertions.assertThat(result).isEqualTo(Month.JANUARY);
    }

    @Test
    public void givenMonths_whenInvalidMonthValueProvided_illegalArgExIsThrown() {
        assertThatIllegalArgumentException().isThrownBy(() -> Month.findByValue("Jan"));
    }
}
