package com.baeldung.offsetdatetime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.OffsetDateTime;

public class  MainJavaTimeModule {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        User user = new User();
        user.setCreatedAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"));

        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);
    }
}
