package com.baeldung.kafkastreams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSerializer implements Serializer<User> {
    private static final Logger log = LoggerFactory.getLogger(UserSerializer.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, User user) {
        if (user == null) {
            return null;
        }

        try {
            return mapper.writeValueAsBytes(user);
        } catch (JsonProcessingException ex) {
            log.error("Error deserializing the user {} with exception {}", user, ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}

