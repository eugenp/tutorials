package com.baeldung.mapstructstringmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class EventMapperUnitTest {

    @Test
    void shouldMapLocalDateToFormattedString() {

        Event event = new Event();
        event.setName("Tech Meetup");
        event.setEventDate(LocalDate.of(2025, 11, 10));

        EventDTO dto = EventMapper.INSTANCE.toEventDTO(event);

        assertNotNull(dto);
        assertEquals("Tech Meetup", dto.getName());
        assertEquals("2025-11-10", dto.getEventDate());
    }
}
