package com.baeldung.kafka.message.ordering;

public class Config {
    public static final String CONSUMER_VALUE_DESERIALIZER_SERIALIZED_CLASS = "value.deserializer.serializedClass";
    public static final String KAFKA_LOCAL = "localhost:9092";
    public static final String MULTI_PARTITION_TOPIC = "multi_partition_topic";
    public static final String SINGLE_PARTITION_TOPIC = "single_partition_topic";
}
