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
    void givenNullEventList_WhenFindMaxDateOf_ThenNull() {
        assertNull(DateHelper.findMaxDateOf(null));
    }

    @Test
    void givenEmptyEventList_WhenFindMaxDateOf_ThenNull() {
        assertNull(DateHelper.findMaxDateOf(List.of()));
    }

    @Test
    void givenSingleElementEventList_WhenFindMaxDateOf_ThenReturnElementDate() {
        assertEquals(TODAY, DateHelper.findMaxDateOf(List.of(TODAYS_EVENT)));
    }

    @Test
    void givenEventList_WhenFindMaxDateOf_ThenReturnMaxDate() {
        assertEquals(NEXT_WEEK, DateHelper.findMaxDateOf(List.of(TODAYS_EVENT, TOMORROWS_EVENT, NEXT_WEEK_EVENT)));
    }

    @Test
    void givenNullEventList_WhenFindMaxDateOfWithComparator_ThenNull() {
        assertNull(DateHelper.findMaxDateOfWithComparator(null));
    }

    @Test
    void givenEmptyEventList_WhenFindMaxDateOfWithComparator_ThenNull() {
        assertNull(DateHelper.findMaxDateOfWithComparator(List.of()));
    }

    @Test
    void givenSingleElementEventList_WhenFindMaxDateOfWithComparator_ThenReturnElementDate() {
        assertEquals(TODAY, DateHelper.findMaxDateOfWithComparator(List.of(TODAYS_EVENT)));
    }

    @Test
    void givenEventList_WhenFindMaxDateOfWithComparator_ThenReturnMaxDate() {
        assertEquals(NEXT_WEEK, DateHelper.findMaxDateOfWithComparator(List.of(TODAYS_EVENT, TOMORROWS_EVENT, NEXT_WEEK_EVENT)));
    }

    @Test
    void givenNullLocalEventList_WhenFindMaxDateOfLocalEvents_ThenNull() {
        assertNull(DateHelper.findMaxDateOfLocalEvents(null));
    }

    @Test
    void givenEmptyLocalEventList_WhenFindMaxDateOfLocalEvents_ThenNull() {
        assertNull(DateHelper.findMaxDateOfLocalEvents(List.of()));
    }

    @Test
    void givenSingleElementLocalEventList_WhenFindMaxDateOfLocalEvents_ThenReturnElementDate() {
        assertEquals(TODAY_LOCAL, DateHelper.findMaxDateOfLocalEvents(List.of(TODAY_LOCAL_EVENT)));
    }

    @Test
    void givenLocalEventList_WhenFindMaxDateOfLocalEvents_ThenReturnMaxDate() {
        assertEquals(NEXT_WEEK_LOCAL, DateHelper.findMaxDateOfLocalEvents(List.of(TODAY_LOCAL_EVENT, TOMORROW_LOCAL_EVENT, NEXT_WEEK_LOCAL_EVENT)));
    }

    @Test
    void givenNullLocalEventList_WhenFindMaxDateOfLocalEventsWithComparator_ThenNull() {
        assertNull(DateHelper.findMaxDateOfLocalEventsWithComparator(null));
    }

    @Test
    void givenEmptyLocalEventList_WhenFindMaxDateOfLocalEventsWithComparator_ThenNull() {
        assertNull(DateHelper.findMaxDateOfLocalEventsWithComparator(List.of()));
    }

    @Test
    void givenSingleElementLocalEventList_WhenFindMaxDateOfLocalEventsWithComparator_ThenReturnElementDate() {
        assertEquals(TODAY_LOCAL, DateHelper.findMaxDateOfLocalEventsWithComparator(List.of(TODAY_LOCAL_EVENT)));
    }

    @Test
    void givenLocalEventList_WhenFindMaxDateOfLocalEventsWithComparator_ThenReturnMaxDate() {
        assertEquals(NEXT_WEEK_LOCAL, DateHelper.findMaxDateOfLocalEventsWithComparator(List.of(TODAY_LOCAL_EVENT, TOMORROW_LOCAL_EVENT, NEXT_WEEK_LOCAL_EVENT)));
    }

}
