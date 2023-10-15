package com.baeldung.kafka.message.ordering.serialization;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JacksonDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> type;

    public JacksonDeserializer(Class<T> type) {
        this.type = type;
    }

    public JacksonDeserializer() {

    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.type = (Class<T>) configs.get("value.deserializer.serializedClass");
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

