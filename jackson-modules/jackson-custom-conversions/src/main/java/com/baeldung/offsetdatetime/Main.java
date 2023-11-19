package com.baeldung.offsetdatetime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.OffsetDateTime;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(serializeUser());
        System.out.println(customSerialize());
        System.out.println(customDeserialize());
    }

    static String serializeUser() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        User user = new User();
        user.setCreatedAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"));

        return objectMapper.writeValueAsString(user);
    }

    static String customSerialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new SimpleModule().addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer()));

        User user = new User();
        user.setCreatedAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"));

        return objectMapper.writeValueAsString(user);
    }

    static String customDeserialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new SimpleModule().addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer()));

        String jsonString = "{\"createdAt\":\"30-09-2021 15:30:00 +01:00\"}";
        User returnedUser = objectMapper.readValue(jsonString, User.class);

        return returnedUser.getCreatedAt().toString();
    }
}
