package com.baeldung.kafkastreams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSerializer implements Serializer<User> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, User user) {
        if (user == null) {
            return null;
        }

        try {
            return mapper.writeValueAsBytes(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

