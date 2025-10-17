package com.baeldung.jpa.localdatetimequery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(EventCriteriaRepository.class)
public class EventRepositoryUnitTest {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCriteriaRepository eventCriteriaRepository;

    private LocalDate testDate;

    @BeforeEach
    public void setUp() {
        testDate = LocalDate.of(2025, 10, 12);

        eventRepository.deleteAll();

        eventRepository.save(new Event("Morning Meeting", LocalDateTime.of(2025, 10, 12, 9, 0, 0)));
        eventRepository.save(new Event("Lunch Discussion", LocalDateTime.of(2025, 10, 12, 12, 30, 0)));
        eventRepository.save(new Event("Evening Review", LocalDateTime.of(2025, 10, 12, 18, 45, 0)));
        eventRepository.save(new Event("Next Day Planning", LocalDateTime.of(2025, 10, 13, 10, 0, 0)));
    }
    @Test
    public void givenLocalDateAndEventsWithTimestamps_whenQueryUsingBetween_thenReturnAllEventsForThatDay() {
        LocalDateTime startOfDay = testDate.atStartOfDay();
        LocalDateTime endOfDay = testDate.plusDays(1).atStartOfDay();

        List<Event> results = eventRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        assertEquals(3, results.size());
        assertEquals("Morning Meeting", results.get(0).getName());
        assertEquals("Lunch Discussion", results.get(1).getName());
        assertEquals("Evening Review", results.get(2).getName());
    }

    @Test
    public void givenLocalDateAndEventsWithTimestamps_whenQueryUsingExplicitBoundaries_thenReturnAllEventsForThatDay() {
        LocalDateTime startOfDay = testDate.atStartOfDay();
        LocalDateTime endOfDay = testDate.plusDays(1).atStartOfDay();

        List<Event> results = eventRepository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(startOfDay, endOfDay);

        assertEquals(3, results.size());
        assertEquals("Morning Meeting", results.get(0).getName());
        assertEquals("Lunch Discussion", results.get(1).getName());
        assertEquals("Evening Review", results.get(2).getName());
    }

//    @Test
//    public void givenLocalDateAndEventsWithTimestamps_whenQueryUsingJpqlDateFunction_thenReturnAllEventsForThatDay() {
//        LocalDate queryDate = LocalDate.of(2025, 10, 12);
//
//        List<Event> results = eventRepository.findByDate(queryDate);
//
//        assertEquals(3, results.size());
//        assertEquals("Morning Meeting", results.get(0).getName());
//        assertEquals("Lunch Discussion", results.get(1).getName());
//        assertEquals("Evening Review", results.get(2).getName());
//    }

    @Test
    public void givenLocalDateAndEventsWithTimestamps_whenQueryUsingCriteriaApi_thenReturnAllEventsForThatDay() {
        LocalDate queryDate = LocalDate.of(2025, 10, 12);

        List<Event> results = eventCriteriaRepository.findByCreatedDate(queryDate);

        assertEquals(3, results.size());
        assertEquals("Morning Meeting", results.get(0).getName());
        assertEquals("Lunch Discussion", results.get(1).getName());
        assertEquals("Evening Review", results.get(2).getName());
    }

    @Test
    public void givenLocalDateAndEventsWithTimestamps_whenQueryUsingNativeSql_thenReturnAllEventsForThatDay() {
        LocalDateTime startOfDay = testDate.atStartOfDay();
        LocalDateTime endOfDay = testDate.plusDays(1).atStartOfDay();

        List<Event> results = eventRepository.findByDateRangeNative(startOfDay, endOfDay);

        assertEquals(3, results.size());
        assertEquals("Morning Meeting", results.get(0).getName());
        assertEquals("Lunch Discussion", results.get(1).getName());
        assertEquals("Evening Review", results.get(2).getName());
    }

    @Test
    public void givenLocalDateForDifferentDay_whenQueryUsingBetween_thenReturnOnlyEventForThatDay() {
        LocalDate differentDate = LocalDate.of(2025, 10, 13);
        LocalDateTime startOfDay = differentDate.atStartOfDay();
        LocalDateTime endOfDay = differentDate.plusDays(1).atStartOfDay();

        List<Event> results = eventRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        assertEquals(1, results.size());
        assertEquals("Next Day Planning", results.get(0).getName());
    }

    @Test
    public void givenNoEventsForDate_whenQueryUsingBetween_thenReturnEmptyList() {
        LocalDate emptyDate = LocalDate.of(2025, 10, 14);
        LocalDateTime startOfDay = emptyDate.atStartOfDay();
        LocalDateTime endOfDay = emptyDate.plusDays(1).atStartOfDay();

        List<Event> results = eventRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        assertEquals(0, results.size());
    }
}
