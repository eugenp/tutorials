package com.baeldung.spring.kafka.streams;

import com.baeldung.spring.kafka.streams.KafkaStreamsConfiguration.CustomValue;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class CustomValueSerde implements Serde<CustomValue> {

    @Override
    public Serializer<CustomValue> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<CustomValue> deserializer() {
        return new JsonDeserializer<>();
    }
}
