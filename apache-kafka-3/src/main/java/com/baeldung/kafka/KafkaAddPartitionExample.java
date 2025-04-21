package com.baeldung.kafka;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewPartitions;

public class KafkaAddPartitionExample {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        try (AdminClient adminClient = AdminClient.create(props)) {
            adminClient.createPartitions(Collections.singletonMap("my-topic", NewPartitions.increaseTo(3)))
                .all()
                .get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
