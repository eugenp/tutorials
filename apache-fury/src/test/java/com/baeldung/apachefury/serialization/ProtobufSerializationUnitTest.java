package com.baeldung.apachefury.serialization;

import com.baeldung.apachefury.event.UserEventProto;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class ProtobufSerializationUnitTest {

    @Test
    void testProtobufSerialization() throws InvalidProtocolBufferException {

        List<UserEventProto.UserEvent> events = new ArrayList<>(100000);
        List<UserEventProto.UserEvent> parserEvents = new ArrayList<>(100000);

        for (int i = 0; i < 100000; i++) {
            events.add(UserEventProto.UserEvent.newBuilder()
                    .setUserId(UUID.randomUUID() +"-"+i)
                    .setEventType("login")
                    .setTimestamp(System.currentTimeMillis())
                    .build());
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            byte[] serializedData = events.get(i).toByteArray();
            UserEventProto.UserEvent temp = UserEventProto.UserEvent.parseFrom(serializedData);
            parserEvents.add(temp);
        }
        long endTime = System.currentTimeMillis();

        long totalBytes = 0;
        for (int i = 0; i < 100000; i++) {
            byte[] serializedData = events.get(i).toByteArray();
            totalBytes += serializedData.length;
        }

        System.out.println("Protocol Buffers serialization time: " + (endTime - startTime) + " ms");
        System.out.println("Total bytes: " + totalBytes);

        Assertions.assertEquals(events, parserEvents);
    }
}
