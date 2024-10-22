package com.baeldung.streams.maxdate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

class DateHelperUnitTest {

    Date TODAY = new Date();
    Event TODAYS_EVENT = new Event(TODAY);
    Date TOMORROW = DateUtils.addDays(TODAY, 1);
    Event TOMORROWS_EVENT = new Event(TOMORROW);
    Date NEXT_WEEK = DateUtils.addDays(TODAY, 7);
    Event NEXT_WEEK_EVENT = new Event(NEXT_WEEK);

    LocalDate TODAY_LOCAL = LocalDate.now();
    LocalEvent TODAY_LOCAL_EVENT = new LocalEvent(TODAY_LOCAL);
    LocalDate TOMORROW_LOCAL = TODAY_LOCAL.plusDays(1);
    LocalEvent TOMORROW_LOCAL_EVENT = new LocalEvent(TOMORROW_LOCAL);
    LocalDate NEXT_WEEK_LOCAL = TODAY_LOCAL.plusWeeks(1);
    LocalEvent NEXT_WEEK_LOCAL_EVENT = new LocalEvent(NEXT_WEEK_LOCAL);

    @Test
    void givenNullEventList_whenFindMaxDateOf_thenNull() {
        assertNull(DateHelper.findMaxDateOf(null));
    }

    @Test
    void givenEmptyEventList_whenFindMaxDateOf_thenNull() {
        assertNull(DateHelper.findMaxDateOf(List.of()));
    }

    @Test
    void givenSingleElementEventList_whenFindMaxDateOf_thenReturnElementDate() {
        assertEquals(TODAY, DateHelper.findMaxDateOf(List.of(TODAYS_EVENT)));
    }

    @Test
    void givenEventList_whenFindMaxDateOf_thenReturnMaxDate() {
        assertEquals(NEXT_WEEK, DateHelper.findMaxDateOf(List.of(TODAYS_EVENT, TOMORROWS_EVENT, NEXT_WEEK_EVENT)));
    }

    @Test
    void givenNullEventList_whenFindMaxDateOfWithComparator_thenNull() {
        assertNull(DateHelper.findMaxDateOfWithComparator(null));
    }

    @Test
    void givenEmptyEventList_whenFindMaxDateOfWithComparator_thenNull() {
        assertNull(DateHelper.findMaxDateOfWithComparator(List.of()));
    }

    @Test
    void givenSingleElementEventList_whenFindMaxDateOfWithComparator_thenReturnElementDate() {
        assertEquals(TODAY, DateHelper.findMaxDateOfWithComparator(List.of(TODAYS_EVENT)));
    }

    @Test
    void givenEventList_whenFindMaxDateOfWithComparator_thenReturnMaxDate() {
        assertEquals(NEXT_WEEK, DateHelper.findMaxDateOfWithComparator(List.of(TODAYS_EVENT, TOMORROWS_EVENT, NEXT_WEEK_EVENT)));
    }
    
    @Test
    void givenNullEventList_whenFindMinDateOf_thenNull() {
        assertNull(DateHelper.findMinDateOf(null));
    }

    @Test
    void givenEmptyEventList_whenFindMinDateOf_thenNull() {
        assertNull(DateHelper.findMinDateOf(List.of()));
    }

    @Test
    void givenSingleElementEventList_whenFindMinDateOf_thenReturnElementDate() {
        assertEquals(TODAY, DateHelper.findMinDateOf(List.of(TODAYS_EVENT)));
    }

    @Test
    void givenEventList_whenFindMinDateOf_thenReturnMinDate() {
        assertEquals(TODAY, DateHelper.findMinDateOf(List.of(TODAYS_EVENT, TOMORROWS_EVENT, NEXT_WEEK_EVENT)));
    }

    @Test
    void givenNullEventList_whenFindMinDateOfWithComparator_thenNull() {
        assertNull(DateHelper.findMinDateOfWithComparator(null));
    }

    @Test
    void givenEmptyEventList_whenFindMinDateOfWithComparator_thenNull() {
        assertNull(DateHelper.findMinDateOfWithComparator(List.of()));
    }

    @Test
    void givenSingleElementEventList_whenFindMinDateOfWithComparator_thenReturnElementDate() {
        assertEquals(TODAY, DateHelper.findMinDateOfWithComparator(List.of(TODAYS_EVENT)));
    }

    @Test
    void givenEventList_whenFindMinDateOfWithComparator_thenReturnMaxDate() {
        assertEquals(TODAY, DateHelper.findMinDateOfWithComparator(List.of(TODAYS_EVENT, TOMORROWS_EVENT, NEXT_WEEK_EVENT)));
    }

    @Test
    void givenNullLocalEventList_whenFindMaxDateOfLocalEvents_thenNull() {
        assertNull(DateHelper.findMaxDateOfLocalEvents(null));
    }

    @Test
    void givenEmptyLocalEventList_whenFindMaxDateOfLocalEvents_thenNull() {
        assertNull(DateHelper.findMaxDateOfLocalEvents(List.of()));
    }

    @Test
    void givenSingleElementLocalEventList_whenFindMaxDateOfLocalEvents_thenReturnElementDate() {
        assertEquals(TODAY_LOCAL, DateHelper.findMaxDateOfLocalEvents(List.of(TODAY_LOCAL_EVENT)));
    }

    @Test
    void givenLocalEventList_whenFindMaxDateOfLocalEvents_thenReturnMaxDate() {
        assertEquals(NEXT_WEEK_LOCAL, DateHelper.findMaxDateOfLocalEvents(List.of(TODAY_LOCAL_EVENT, TOMORROW_LOCAL_EVENT, NEXT_WEEK_LOCAL_EVENT)));
    }

    @Test
    void givenNullLocalEventList_whenFindMaxDateOfLocalEventsWithComparator_thenNull() {
        assertNull(DateHelper.findMaxDateOfLocalEventsWithComparator(null));
    }

    @Test
    void givenEmptyLocalEventList_whenFindMaxDateOfLocalEventsWithComparator_thenNull() {
        assertNull(DateHelper.findMaxDateOfLocalEventsWithComparator(List.of()));
    }

    @Test
    void givenSingleElementLocalEventList_whenFindMaxDateOfLocalEventsWithComparator_thenReturnElementDate() {
        assertEquals(TODAY_LOCAL, DateHelper.findMaxDateOfLocalEventsWithComparator(List.of(TODAY_LOCAL_EVENT)));
    }

    @Test
    void givenLocalEventList_whenFindMaxDateOfLocalEventsWithComparator_thenReturnMaxDate() {
        assertEquals(NEXT_WEEK_LOCAL, DateHelper.findMaxDateOfLocalEventsWithComparator(List.of(TODAY_LOCAL_EVENT, TOMORROW_LOCAL_EVENT, NEXT_WEEK_LOCAL_EVENT)));
    }

    @Test
    void givenNullLocalEventList_whenFindinxDateOfLocalEvents_thenNull() {
        assertNull(DateHelper.findMinDateOfLocalEvents(null));
    }

    @Test
    void givenEmptyLocalEventList_whenFindMinDateOfLocalEvents_thenNull() {
        assertNull(DateHelper.findMinDateOfLocalEvents(List.of()));
    }

    @Test
    void givenSingleElementLocalEventList_whenFindMinDateOfLocalEvents_thenReturnElementDate() {
        assertEquals(TODAY_LOCAL, DateHelper.findMinDateOfLocalEvents(List.of(TODAY_LOCAL_EVENT)));
    }

    @Test
    void givenLocalEventList_whenFindMinDateOfLocalEvents_thenReturnMaxDate() {
        assertEquals(TODAY_LOCAL, DateHelper.findMinDateOfLocalEvents(List.of(TODAY_LOCAL_EVENT, TOMORROW_LOCAL_EVENT, NEXT_WEEK_LOCAL_EVENT)));
    }

    @Test
    void givenNullLocalEventList_whenFindMinDateOfLocalEventsWithComparator_thenNull() {
        assertNull(DateHelper.findMinDateOfLocalEventsWithComparator(null));
    }

    @Test
    void givenEmptyLocalEventList_whenFindMinDateOfLocalEventsWithComparator_thenNull() {
        assertNull(DateHelper.findMinDateOfLocalEventsWithComparator(List.of()));
    }

    @Test
    void givenSingleElementLocalEventList_whenFindMinDateOfLocalEventsWithComparator_thenReturnElementDate() {
        assertEquals(TODAY_LOCAL, DateHelper.findMinDateOfLocalEventsWithComparator(List.of(TODAY_LOCAL_EVENT)));
    }

    @Test
    void givenLocalEventList_whenFindMinDateOfLocalEventsWithComparator_thenReturnMaxDate() {
        assertEquals(TODAY_LOCAL, DateHelper.findMinDateOfLocalEventsWithComparator(List.of(TODAY_LOCAL_EVENT, TOMORROW_LOCAL_EVENT, NEXT_WEEK_LOCAL_EVENT)));
    }

    @Test
    void givenEventList_whenFindMaxDateWithCollections_thenReturnMaxDate() {
        assertEquals(NEXT_WEEK, DateHelper.findMaxDateWithCollections(List.of(TODAYS_EVENT, TOMORROWS_EVENT, NEXT_WEEK_EVENT)));
    }

    @Test
    void givenLocalEventList_whenFindMaxLocalDateWithCollections_thenReturnMaxDate() {
        assertEquals(NEXT_WEEK_LOCAL, DateHelper.findMaxLocalDateWithCollections(List.of(TODAY_LOCAL_EVENT, TOMORROW_LOCAL_EVENT, NEXT_WEEK_LOCAL_EVENT)));
    }
}
