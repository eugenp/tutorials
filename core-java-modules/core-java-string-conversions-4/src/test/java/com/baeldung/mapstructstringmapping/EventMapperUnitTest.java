package com.baeldung.mapstructstringmapping;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventMapperUnitTest {

    @Test
    void shouldMapDateToFormattedString() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2025-11-10");
        Event event = new Event("Tech Meetup", date);

        EventDTO dto = EventMapper.INSTANCE.toEventDTO(event);

        assertNotNull(dto);
        assertEquals("Tech Meetup", dto.getName());
        assertEquals("2025-11-10", dto.getEventDate());
    }
}
