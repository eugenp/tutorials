package com.baeldung.assertj;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.time.LocalDate.ofYearDay;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class AssertJJava8UnitTest {

    @Test
    public void givenOptional_shouldAssert() throws Exception {
        final Optional<String> givenOptional = Optional.of("something");

        assertThat(givenOptional).isPresent().hasValue("something");
    }

    @Test
    public void givenPredicate_shouldAssert() throws Exception {
        final Predicate<String> predicate = s -> s.length() > 4;

        assertThat(predicate).accepts("aaaaa", "bbbbb").rejects("a", "b").acceptsAll(asList("aaaaa", "bbbbb")).rejectsAll(asList("a", "b"));
    }

    @Test
    public void givenLocalDate_shouldAssert() throws Exception {
        final LocalDate givenLocalDate = LocalDate.of(2016, 7, 8);
        final LocalDate todayDate = LocalDate.now();

        assertThat(givenLocalDate).isBefore(LocalDate.of(2020, 7, 8)).isAfterOrEqualTo(LocalDate.of(1989, 7, 8));

        assertThat(todayDate).isAfter(LocalDate.of(1989, 7, 8)).isToday();
    }

    @Test
    public void givenLocalDateTime_shouldAssert() throws Exception {
        final LocalDateTime givenLocalDate = LocalDateTime.of(2016, 7, 8, 12, 0);

        assertThat(givenLocalDate).isBefore(LocalDateTime.of(2020, 7, 8, 11, 2));
    }

    @Test
    public void givenLocalTime_shouldAssert() throws Exception {
        final LocalTime givenLocalTime = LocalTime.of(12, 15);

        assertThat(givenLocalTime).isAfter(LocalTime.of(1, 0)).hasSameHourAs(LocalTime.of(12, 0));
    }

    @Test
    public void givenList_shouldAssertFlatExtracting() throws Exception {
        final List<LocalDate> givenList = asList(ofYearDay(2016, 5), ofYearDay(2015, 6));

        assertThat(givenList).flatExtracting(LocalDate::getYear).contains(2015);
    }

    @Test
    public void givenList_shouldAssertFlatExtractingLeapYear() throws Exception {
        final List<LocalDate> givenList = asList(ofYearDay(2016, 5), ofYearDay(2015, 6));

        assertThat(givenList).flatExtracting(LocalDate::isLeapYear).contains(true);
    }

    @Test
    public void givenList_shouldAssertFlatExtractingClass() throws Exception {
        final List<LocalDate> givenList = asList(ofYearDay(2016, 5), ofYearDay(2015, 6));

        assertThat(givenList).flatExtracting(Object::getClass).contains(LocalDate.class);
    }

    @Test
    public void givenList_shouldAssertMultipleFlatExtracting() throws Exception {
        final List<LocalDate> givenList = asList(ofYearDay(2016, 5), ofYearDay(2015, 6));

        assertThat(givenList).flatExtracting(LocalDate::getYear, LocalDate::getDayOfMonth).contains(2015, 6);
    }

    @Test
    public void givenString_shouldSatisfy() throws Exception {
        final String givenString = "someString";

        assertThat(givenString).satisfies(s -> {
            assertThat(s).isNotEmpty();
            assertThat(s).hasSize(10);
        });
    }

    @Test
    public void givenString_shouldMatch() throws Exception {
        final String emptyString = "";

        assertThat(emptyString).matches(String::isEmpty);
    }

    @Test
    public void givenList_shouldHasOnlyOneElementSatisfying() throws Exception {
        final List<String> givenList = Arrays.asList("");

        assertThat(givenList).hasOnlyOneElementSatisfying(s -> assertThat(s).isEmpty());
    }
}
