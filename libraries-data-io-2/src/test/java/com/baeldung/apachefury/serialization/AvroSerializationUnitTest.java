package com.baeldung.apachefury.serialization;

import com.baeldung.apachefury.event.avro.Address;
import com.baeldung.apachefury.event.avro.UserEvent;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

class AvroSerializationUnitTest {

    static final Logger LOG = Logger.getLogger(AvroSerializationUnitTest.class.getName());

    @Test
    void whenUsingAvroSerialization_thenGenerateByteOutput() throws IOException {

        List<UserEvent> events = new ArrayList<>(100000);
        List<UserEvent> parserEvents = new ArrayList<>(100000);

        for (int i = 0; i < 100000; i++) {
            events.add(UserEvent.newBuilder()
                    .setUserId(UUID.randomUUID()+"-"+i)
                    .setEventType("login")
                    .setTimestamp(System.currentTimeMillis())
                    .setAddress(Address.newBuilder()
                            .setStreet(i + " Main St")
                            .setCity("Spring field " + i)
                            .setZipCode(UUID.randomUUID().toString())
                            .build())
                    .build());
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            byte[] serializedData = serializeAvro(events.get(i));
            UserEvent temp = deSerializeAvro(serializedData);
            parserEvents.add(temp);
        }
        long endTime = System.currentTimeMillis();

        long totalBytes = 0;
        for (int i = 0; i < 100000; i++) {
            byte[] serializedData = serializeAvro(events.get(i));
            totalBytes += serializedData.length;
        }

        LOG.info("Avro serialization time: " + (endTime - startTime) + " ms");
        LOG.info("Total bytes: " + totalBytes / (1024 * 1024) + " MB");

        Assertions.assertEquals(events, parserEvents);
    }

    public byte[] serializeAvro(UserEvent request) {
        DatumWriter<UserEvent> writer = new SpecificDatumWriter<>(UserEvent.class);;
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            final Encoder jsonEncoder = EncoderFactory.get().jsonEncoder(UserEvent.getClassSchema(), stream);
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            return stream.toByteArray();
        } catch (IOException e) {
            // handle exception
            return new byte[0];
        }
    }

    public UserEvent deSerializeAvro(byte[] data) {
        DatumReader<UserEvent> reader = new SpecificDatumReader<>(UserEvent.class);
        try {
            Decoder decoder = DecoderFactory.get().jsonDecoder(UserEvent.getClassSchema(), new String(data));
            return reader.read(null, decoder);
        } catch (IOException e) {
            // handle exception
            return null;
        }
    }
}
