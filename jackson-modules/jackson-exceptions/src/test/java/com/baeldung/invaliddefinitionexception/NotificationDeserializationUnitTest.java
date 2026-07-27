package com.baeldung.invaliddefinitionexception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.module.paranamer.ParanamerModule;

class NotificationDeserializationUnitTest {

    private static final String JSON = "{\"message\":\"Server maintenance\",\"priority\":2}";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenNotificationWithoutCreator_whenDeserializing_thenThrowException() {
        InvalidDefinitionException exception = assertThrows(InvalidDefinitionException.class, () -> objectMapper.readValue(JSON, Notification.class));

        assertTrue(exception.getMessage()
            .contains("no Creators, like default constructor, exist"));
    }

    @Test
    void givenNotificationWithDefaultConstructor_whenDeserializing_thenCorrect() throws Exception {

        NotificationWithDefaultConstructor notification = objectMapper.readValue(JSON, NotificationWithDefaultConstructor.class);

        assertEquals("Server maintenance", notification.getMessage());
        assertEquals(2, notification.getPriority());
    }

    @Test
    void givenNotificationWithCreator_whenDeserializing_thenCorrect() throws Exception {

        NotificationWithCreator notification = objectMapper.readValue(JSON, NotificationWithCreator.class);

        assertEquals("Server maintenance", notification.getMessage());
        assertEquals(2, notification.getPriority());
    }

    @Test
    void givenParanamerModule_whenDeserializingNotification_thenCorrect() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParanamerModule());

        Notification notification = mapper.readValue(JSON, Notification.class);

        assertEquals("Server maintenance", notification.getMessage());
        assertEquals(2, notification.getPriority());
    }
}