package com.baeldung.spring.kafka.partitioningstrategy;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class CustomPartitioner implements Partitioner {
    private static final int PREMIUM_PARTITION = 0;
    private static final int NORMAL_PARTITION = 1;

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String customerType = extractCustomerType(key.toString());
        return "premium".equalsIgnoreCase(customerType) ? PREMIUM_PARTITION : NORMAL_PARTITION;
    }

    private String extractCustomerType(String key) {
        String[] parts = key.split("_");
        return parts.length > 1 ? parts[1] : "normal";
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public void close() {

    }
}
