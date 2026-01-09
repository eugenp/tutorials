package com.baeldung.mapstructstringmapping;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EventMapperUnitTest {

    @Test
    void givenEventWithLocalDate_whenMapsToEventDTO_thenDateIsFormattedAsString() {

        LocalDate date = LocalDate.of(2025, 11, 10);
        Event event = new Event();
        event.setName("Tech Meetup");
        event.setEventDate(date);

        EventDTO dto = EventMapper.INSTANCE.toEventDTO(event);

        assertNotNull(dto);
        assertEquals("Tech Meetup", dto.getName());
        assertEquals("2025-11-10", dto.getEventDate());
    }
}
