package com.baeldung.offsetdatetime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.OffsetDateTime;

public class MainCustomSerialization {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Serialization
        objectMapper.registerModule(new SimpleModule().addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer()));

        User user = new User();
        user.setCreatedAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"));

        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);

        // Deserialization
        objectMapper.registerModule(new SimpleModule().addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer()));

        String jsonString = "{\"createdAt\":\"30-09-2021 15:30:00 +01:00\"}";
        User returnedUser = objectMapper.readValue(jsonString, User.class);

        System.out.println(returnedUser.getCreatedAt());

    }
}
