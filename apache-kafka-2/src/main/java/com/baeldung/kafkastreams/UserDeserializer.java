package com.baeldung.kafkastreams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class UserDeserializer implements Deserializer<User> {
    private final ObjectMapper mapper = new ObjectMapper();;

    @Override
    public User deserialize(String topic, byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        try {
            return mapper.readValue(bytes, User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

