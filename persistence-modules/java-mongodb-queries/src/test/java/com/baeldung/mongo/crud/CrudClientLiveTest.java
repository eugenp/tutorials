package com.baeldung.mongo.crud;

import com.baeldung.mongo.crud.model.Event;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudClientLiveTest {

    @BeforeAll
    public static void setup() {
        CrudClient.setup();
    }

    @Test
    @Order(1)
    public void whenInsertingEventsWithDate_thenCheckForDocument() {
        Assertions.assertNotNull(CrudClient.insertEventsWithDate(CrudClient.pianoLessons));
        Assertions.assertNotNull(CrudClient.insertEventsWithDate(CrudClient.soccerGame));
    }

    @Test
    @Order(2)
    public void whenReadingEventsByDate_thenCheckForReturnedDocument() {
        Assertions.assertNotNull(CrudClient.readEventsByDate(CrudClient.dateQuery));
        Event event = CrudClient.readEventsByDate(CrudClient.dateQuery);
        Assertions.assertNotNull(event);
        Assertions.assertEquals("Soccer game", event.title);
        Assertions.assertEquals("Bar Avenue", event.location);
        Assertions.assertEquals(CrudClient.soccerGame.dateTime, event.dateTime);
    }

    @Test
    @Order(3)
    public void whenReadingEventsByDateRange_thenCheckForReturnedDocument() {
        List<Event> events = CrudClient.readEventsByDateRange(CrudClient.from, CrudClient.to);
        Assertions.assertNotNull(events);
        Assertions.assertEquals(1, events.size());
        Assertions.assertEquals("Soccer game", events.get(0).title);
        Assertions.assertEquals("Bar Avenue", events.get(0).location);
        Assertions.assertEquals(CrudClient.soccerGame.dateTime, events.get(0).dateTime);
    }

    @Test
    @Order(5)
    public void whenUpdatingEventsDateField_thenCheckUpdatedCount() {
        Assertions.assertEquals(1, CrudClient.updateDateField());
    }

    @Test
    @Order(6)
    public void whenUpdatingManyEvents_thenCheckUpdatedCount() {
        long updates = CrudClient.updateManyEventsWithDateCriteria(CrudClient.updateManyFrom, CrudClient.updateManyTo);
        Assertions.assertTrue(1 < updates);
    }

    @Test
    @Order(7)
    public void whenDeletingEventsWithDate_thenCheckDeletedCount() {
        Assertions.assertEquals(2, CrudClient.deleteEventsByDate(CrudClient.deleteFrom, CrudClient.deleteTo));
    }

    @Test
    @Order(8)
    public void whenInsertingEventWithDateAndTimeZone_thenCheckForDocument() {
        Assertions.assertNotNull(CrudClient.insertEventsWithDate(CrudClient.pianoLessonsTZ));
    }

    @Test
    @Order(9)
    public void whenReadingEventsWithDateAndTimeZone_thenCheckInsertedCount() {
        Assertions.assertNotEquals(CrudClient.pianoLessonsTZ.dateTime, CrudClient.readEventsByDateWithTZ(CrudClient.dateQueryEventWithTZ));
    }

    @Test
    @Order(10)
    public void whenDeletingEventsWithDateAndTimeZone_thenCheckDeletedCount() {
        Assertions.assertEquals(1, CrudClient.deleteEventsByDate(CrudClient.deleteFrom, CrudClient.deleteTo));
    }

    @AfterAll
    public static void close() throws IOException {
        CrudClient.dropDb();
        CrudClient.close();
    }

}
