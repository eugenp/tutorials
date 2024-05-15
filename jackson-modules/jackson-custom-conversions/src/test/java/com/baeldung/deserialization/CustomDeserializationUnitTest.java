package com.baeldung.deserialization;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomDeserializationUnitTest {

    @Test
    public final void whenDeserializingTheStandardRepresentation_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"owner\":{\"id\":2,\"name\":\"theUser\"}}";

        final Item readValue = new ObjectMapper().readValue(json, Item.class);
        assertThat(readValue, notNullValue());
    }

    @Test
    public final void whenDeserializingANonStandardRepresentation_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"createdBy\":2}";
        final ObjectMapper mapper = new ObjectMapper();

        final SimpleModule module = new SimpleModule();
        module.addDeserializer(Item.class, new ItemDeserializer());
        mapper.registerModule(module);

        final Item readValue = mapper.readValue(json, Item.class);
        assertThat(readValue, notNullValue());
    }

    @Test
    public final void givenDeserializerIsOnClass_whenDeserializingCustomRepresentation_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"owner\":2}";

        final ItemWithDeserializer readValue = new ObjectMapper().readValue(json, ItemWithDeserializer.class);
        assertThat(readValue, notNullValue());
    }

    @Test
    public void whenDeserialisingZonedDateTimeWithDefaults_thenTimeZoneIsNotPreserved() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // construct a new instance of ZonedDateTime
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        String converted = objectMapper.writeValueAsString(now);
        // restore an instance of ZonedDateTime from String
        ZonedDateTime restored = objectMapper.readValue(converted, ZonedDateTime.class);
        assertThat(now, is(not(restored)));
    }

    @Test
    public void whenDeserialisingZonedDateTimeWithFeaturesDisabled_thenTimeZoneIsPreserved() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        // construct a new instance of ZonedDateTime
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        String converted = objectMapper.writeValueAsString(now);
        // restore an instance of ZonedDateTime from String
        ZonedDateTime restored = objectMapper.readValue(converted, ZonedDateTime.class);
        assertThat(restored, is(now));
    }

    @Test
    public void whenDeserialisingItemWithWrappedUser_ThenWrappedUserIsCorrectlyParsed() throws JsonProcessingException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"owner\":{\"id\":2,\"name\":\"theUser\"}}";

        SimpleModule module = new SimpleModule().addDeserializer(Wrapper.class, new WrapperDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        final ItemWithWrappedUser readValue = objectMapper.readValue(json, ItemWithWrappedUser.class);

        assertEquals(2, readValue.getOwner()
            .getValue()
            .getId());
        assertEquals("theUser", readValue.getOwner()
            .getValue()
            .getName());
    }

}
