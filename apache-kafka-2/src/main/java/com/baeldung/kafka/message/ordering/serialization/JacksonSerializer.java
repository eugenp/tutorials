package com.baeldung.kafka.message.ordering.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.serialization.Serializer;

/**
 * Configured via {@link org.apache.kafka.clients.producer.ProducerConfig#VALUE_SERIALIZER_CLASS_CONFIG}
 */
public class JacksonSerializer<T> implements Serializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing value", e);
        }
    }
}
