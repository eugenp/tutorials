package com.baeldung.formatinstantobjectmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.baeldung.formatinstantobjectmapper.serializer.GlobalInstantDeserializer;
import com.baeldung.formatinstantobjectmapper.serializer.GlobalInstantSerializer;
import com.baeldung.formatinstantobjectmapper.utils.Instants;
import com.baeldung.formatinstantobjectmapper.utils.TimeStampTracker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class InstantFormatUnitTest {

    private static final String DATE_TEXT = "2024-05-27 12:34:56.789";
    private static final Instant DATE = Instant.from(Instants.FORMATTER.parse(DATE_TEXT));

    private void assertSerializedInstantMatchesWhenDeserialized(TimeStampTracker object, ObjectMapper mapper) throws JsonProcessingException {
        String json = mapper.writeValueAsString(object);
        assertTrue(json.contains(DATE_TEXT));

        TimeStampTracker deserialized = mapper.readValue(json, object.getClass());
        assertEquals(DATE, deserialized.getTimeStamp());
    }

    @Test
    void givenDefaultMapper_whenUsingCustomSerializerDeserializer_thenExpectedInstantFormat() throws JsonProcessingException {
        Event object = new Event();
        object.setTimeStamp(DATE);

        ObjectMapper mapper = new ObjectMapper();

        assertSerializedInstantMatchesWhenDeserialized(object, mapper);
    }

    @Test
    void givenTimeModuleMapper_whenJsonFormat_thenExpectedInstantFormat() throws JsonProcessingException {
        Session object = new Session();
        object.setTimeStamp(DATE);

        ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

        assertSerializedInstantMatchesWhenDeserialized(object, mapper);
    }

    @Test
    void givenTimeModuleMapper_whenSerializingAndDeserializing_thenExpectedInstantFormat() throws JsonProcessingException {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(Instant.class, new GlobalInstantSerializer());
        module.addDeserializer(Instant.class, new GlobalInstantDeserializer());

        History object = new History();
        object.setTimeStamp(DATE);

        ObjectMapper mapper = JsonMapper.builder()
            .addModule(module)
            .build();

        assertSerializedInstantMatchesWhenDeserialized(object, mapper);
    }
}
