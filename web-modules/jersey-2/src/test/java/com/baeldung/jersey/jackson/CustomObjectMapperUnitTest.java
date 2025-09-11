package com.baeldung.jersey.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.jersey.jackson.mapper.ConditionalObjectMapperResolver;
import com.baeldung.jersey.jackson.model.InternalApiMessage;
import com.baeldung.jersey.jackson.model.Message;
import com.baeldung.jersey.jackson.model.PublicApiMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapperUnitTest {
    private ObjectMapper defaultMapper;
    private ObjectMapper publicApiMapper;
    private ObjectMapper internalApiMapper;

    @BeforeEach
    void setUp() {
        ConditionalObjectMapperResolver resolver = new ConditionalObjectMapperResolver();
        publicApiMapper = resolver.getContext(PublicApiMessage.class);
        internalApiMapper = resolver.getContext(InternalApiMessage.class);
        defaultMapper = resolver.getContext(Message.class);
    }

    @Test
    void givenPublicApiMessage_whenSerialized_thenOmitsSensitiveFieldAndNulls() throws Exception {
        PublicApiMessage message = new PublicApiMessage("Public Hello!", LocalDate.of(2025, 8, 23), null);

        String json = publicApiMapper.writeValueAsString(message);

        assertTrue(json.contains("text"));
        assertTrue(json.contains("date"));
        assertFalse(json.contains("sensitiveField"), "sensitiveField should not appear");
        assertFalse(json.contains("null"), "Null values should be excluded");
    }

    @Test
    void givenInternalApiMessageWithEmptyMetadata_whenSerialized_thenIncludesEmptyArraysButNoNulls() throws Exception {
        InternalApiMessage message = new InternalApiMessage("Internal Hello!", LocalDate.of(2025, 8, 23), "debug-123", new ArrayList<>());

        String json = internalApiMapper.writeValueAsString(message);

        assertTrue(json.contains("debugInfo"));
        assertFalse(json.contains("null"), "Null values should be excluded");
        assertFalse(json.contains("metadata"), "Empty metadata list should be excluded");
    }

    @Test
    void givenInternalApiMessageWithNonEmptyMetadata_whenSerialized_thenMetadataIsIncluded() throws Exception {
        InternalApiMessage message = new InternalApiMessage("Internal Hello!", LocalDate.of(2025, 8, 23), "debug-123", Arrays.asList("meta1"));

        String json = internalApiMapper.writeValueAsString(message);

        assertTrue(json.contains("metadata"), "Non-empty metadata should be serialized");
    }

    @Test
    void givenDefaultMessage_whenSerialized_thenIncludesOptionalFieldAndMetadata() throws Exception {
        Message message = new Message("Default Hello!", LocalDate.of(2025, 8, 23), "optional");
        message.metadata = new ArrayList<>();

        String json = defaultMapper.writeValueAsString(message);

        assertTrue(json.contains("metadata"));
        assertTrue(json.contains("optionalField") || json.contains("optional"), "Optional field should be included");
    }

    @Test
    void givenMessageWithDate_whenSerialized_thenDateIsInIso8601Format() throws Exception {
        Message message = new Message("Date Test", LocalDate.of(2025, 9, 2), "optional");

        String json = defaultMapper.writeValueAsString(message);

        assertTrue(json.contains("2025-09-02"), "Date should be serialized in ISO-8601 format");
    }
}
