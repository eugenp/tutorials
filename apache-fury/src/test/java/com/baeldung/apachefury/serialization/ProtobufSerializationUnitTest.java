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
    void whenUsingProtobufSerialization_thenGenerateByteOutput() throws InvalidProtocolBufferException {

        List<UserEventProto.UserEvent> events = new ArrayList<>(100000);
        List<UserEventProto.UserEvent> parserEvents = new ArrayList<>(100000);

        for (int i = 0; i < 100000; i++) {
            events.add(UserEventProto.UserEvent.newBuilder()
                    .setUserId(UUID.randomUUID() +"-"+i)
                    .setEventType("login")
                    .setTimestamp(System.currentTimeMillis())
                    .setAddress(UserEventProto.Address.newBuilder()
                            .setStreet(i + " Main St")
                            .setCity("Spring field " + i)
                            .setZipCode(UUID.randomUUID().toString())
                            .build())
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
        System.out.println("Total bytes: " + (totalBytes / (1024 * 1024)) + " MB");

        Assertions.assertEquals(events, parserEvents);
    }
}
