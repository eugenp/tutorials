package com.baeldung.kafka.admin;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsOptions;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.TopicConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaTopicApplication {

    private final Properties properties;

    public KafkaTopicApplication(Properties properties) {
        this.properties = properties;
    }

    public void createTopic(String topicName) throws Exception {
        try (Admin admin = Admin.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

            CreateTopicsResult result = admin.createTopics(
              Collections.singleton(newTopic));

            // get the async result for the new topic creation
            KafkaFuture<Void> future = result.values().get(topicName);

            // call get() to block until topic creation has completed or failed
            future.get();
        }
    }

    public void createTopicWithOptions(String topicName) throws Exception {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (Admin admin = Admin.create(props)) {
            int partitions = 1;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

            CreateTopicsOptions topicOptions = new CreateTopicsOptions()
              .validateOnly(true)
              .retryOnQuotaViolation(true);

            CreateTopicsResult result = admin.createTopics(
              Collections.singleton(newTopic), topicOptions
            );

            KafkaFuture<Void> future = result.values().get(topicName);
            future.get();
        }
    }

    public void createCompactedTopicWithCompression(String topicName) throws Exception {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (Admin admin = Admin.create(props)) {
            int partitions = 1;
            short replicationFactor = 1;

            // Create a compacted topic with 'lz4' compression codec
            Map<String, String> newTopicConfig = new HashMap<>();
            newTopicConfig.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_COMPACT);
            newTopicConfig.put(TopicConfig.COMPRESSION_TYPE_CONFIG, "lz4");
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor)
              .configs(newTopicConfig);

            CreateTopicsResult result = admin.createTopics(
              Collections.singleton(newTopic)
            );

            KafkaFuture<Void> future = result.values().get(topicName);
            future.get();
        }
    }

}
