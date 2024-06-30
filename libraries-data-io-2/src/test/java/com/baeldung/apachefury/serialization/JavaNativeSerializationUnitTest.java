package com.baeldung.apachefury.serialization;

import com.baeldung.apachefury.event.Address;
import com.baeldung.apachefury.event.UserEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

class JavaNativeSerializationUnitTest {

    static final Logger LOG = Logger.getLogger(JavaNativeSerializationUnitTest.class.getName());

    @Test
    void whenUsingJavaNativeSerialization_thenGenerateByteOutput() throws IOException {
        List<UserEvent> events = new ArrayList<>(100000);
        List<UserEvent> parserEvents = new ArrayList<>(100000);

        for (int i = 0; i < 100000; i++) {
            final Address address = new Address(i+" Main St", "Spring field "+i, UUID.randomUUID().toString());
            events.add(new UserEvent(UUID.randomUUID()+"-"+i, "login", System.currentTimeMillis(), address));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {

            final byte[] serializedData;

            try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
                 final ObjectOutputStream oos = new ObjectOutputStream(out)) {
                oos.writeObject(events.get(i));
                serializedData = out.toByteArray();
            }

            try (final ByteArrayInputStream in = new ByteArrayInputStream(serializedData);
                 final ObjectInputStream ois = new ObjectInputStream(in)) {

                parserEvents.add((UserEvent) ois.readObject());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        long endTime = System.currentTimeMillis();

        long totalBytes = 0;
        for (int i = 0; i < 100000; i++) {
            try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
                 final ObjectOutputStream oos = new ObjectOutputStream(out)) {

                oos.writeObject(events.get(i));
                totalBytes += out.toByteArray().length;
            }
        }

        LOG.info("Java Native serialization time: " + (endTime - startTime) + " ms");
        LOG.info("Total bytes: " + totalBytes / (1024 * 1024) + " MB");

        Assertions.assertEquals(events, parserEvents);
    }
}
