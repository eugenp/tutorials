package com.baeldung.mongo;

import com.baeldung.mongo.crud.CrudClient;
import com.baeldung.mongo.crud.model.Event;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudClientUnitTest {

    @BeforeAll
    public static void setup() {
        CrudClient.setup();
    }

    @Test
    @Order(1)
    public void whenInsertingEventsWithDate_thenCheckForDocument() {
        assertNotNull(CrudClient.insertEventsWithDate(CrudClient.pianoLessons));
        assertNotNull(CrudClient.insertEventsWithDate(CrudClient.soccerGame));
    }

    @Test
    @Order(2)
    public void whenReadingEventsByDate_thenCheckForReturnedDocument() {
        assertNotNull(CrudClient.readEventsByDate(CrudClient.dateQuery));
    }

    @Test
    @Order(3)
    public void whenReadingEventsByDateRange_thenCheckForReturnedDocument() {
        List<Event> events = CrudClient.readEventsByDateRange(CrudClient.from, CrudClient.to);
        assertNotNull(events);
        assertEquals(2, events.size());
    }

    @Test
    @Order(4)
    public void whenUpdatingEventsWithDate_thenCheckUpdatedCount() {
        assertEquals(1, CrudClient.updateEventsWithDate());
    }

    @Test
    @Order(5)
    public void whenUpdatingEventsByReplacing_thenCheckUpdatedCount() {
        assertEquals(1, CrudClient.updateEventByReplacing());
    }

    @Test
    @Order(6)
    public void whenDeletingEventsWithDate_thenCheckDeletedCount() {
        assertEquals(2, CrudClient.deleteEventsByDate(CrudClient.from, CrudClient.to));
    }

    @AfterAll
    public static void close() throws IOException {
        CrudClient.close();
    }

}
