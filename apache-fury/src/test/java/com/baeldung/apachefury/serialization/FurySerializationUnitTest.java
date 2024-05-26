package com.baeldung.apachefury.serialization;

import com.baeldung.apachefury.event.UserEvent;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

;

class FurySerializationUnitTest {

    @Test
    void testFurySerialization() {
        Fury fury = Fury.builder().withLanguage(Language.JAVA)
                .requireClassRegistration(true)
                .build();
        fury.register(UserEvent.class);

        List<UserEvent> events = new ArrayList<>(100000);
        List<UserEvent> parserEvents = new ArrayList<>(100000);

        for (int i = 0; i < 100000; i++) {
            events.add(new UserEvent(UUID.randomUUID()+"-"+i, "login", System.currentTimeMillis()));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            byte[] serializedData = fury.serialize(events.get(i));
            UserEvent temp = (UserEvent) fury.deserialize(serializedData);
            parserEvents.add(temp);
        }
        long endTime = System.currentTimeMillis();

        long totalBytes = 0;
        for (int i = 0; i < 100000; i++) {
            byte[] serializedData = fury.serialize(events.get(i));
            totalBytes += serializedData.length;
        }

        System.out.println("Apache Fury serialization time: " + (endTime - startTime) + " ms");
        System.out.println("Total bytes: " + totalBytes);

        Assertions.assertEquals(events, parserEvents);
    }
}
