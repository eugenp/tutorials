package com.baeldung.apachefury.serialization;

import com.baeldung.apachefury.event.Address;
import com.baeldung.apachefury.event.UserEvent;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

class FurySerializationUnitTest {

    static final Logger LOG = Logger.getLogger(FurySerializationUnitTest.class.getName());

    @Test
    void whenUsingFurySerialization_thenGenerateByteOutput() {

        Fury fury = Fury.builder()
                        .withLanguage(Language.JAVA)
                        .withAsyncCompilation(true)
                        .build();

        fury.register(UserEvent.class);
        fury.register(Address.class);

        List<UserEvent> events = new ArrayList<>(100000);
        List<UserEvent> parserEvents = new ArrayList<>(100000);

        for (int i = 0; i < 100000; i++) {
            final Address address = new Address(i+" Main St", "Spring field "+i, UUID.randomUUID().toString());
            events.add(new UserEvent(UUID.randomUUID()+"-"+i, "login", System.currentTimeMillis(), address));
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

        LOG.info("Apache Fury serialization time: " + (endTime - startTime) + " ms");
        LOG.info("Total bytes: " + totalBytes / (1024 * 1024) + " MB");

        Assertions.assertEquals(events, parserEvents);
    }
}
