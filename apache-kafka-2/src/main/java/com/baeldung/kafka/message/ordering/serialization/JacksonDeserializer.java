package com.baeldung.kafka.message.ordering.serialization;

import com.baeldung.kafka.message.ordering.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Configured via {@link org.apache.kafka.clients.consumer.ConsumerConfig#VALUE_DESERIALIZER_CLASS_CONFIG}
 */
public class JacksonDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> type;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.type = (Class<T>) configs.get(Config.CONSUMER_VALUE_DESERIALIZER_SERIALIZED_CLASS);
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, type);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing value", e);
        }
    }
}

