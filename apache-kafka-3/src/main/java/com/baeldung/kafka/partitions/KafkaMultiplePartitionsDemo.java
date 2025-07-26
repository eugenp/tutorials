package com.baeldung.kafka.partitions;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMultiplePartitionsDemo {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMultiplePartitionsDemo.class);
    private final KafkaProducer<String, String> producer;
    private final String bootstrapServers;

    public KafkaMultiplePartitionsDemo(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
        this.producer = createProducer();
    }

    private KafkaProducer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        return new KafkaProducer<>(props);
    }

    public void sendMessagesWithKey() {
        String key = "user-123";

        for (int i = 0; i < 5; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("user-events", key, "Event " + i);

            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    logger.info("Key: {}, Partition: {}, Offset: {}", key, metadata.partition(), metadata.offset());
                }
            });
        }
        producer.flush();
    }

    public Map<Integer, Integer> sendMessagesWithoutKey() {
        Map<Integer, Integer> partitionCounts = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("events", null,  // no key
                "Message " + i);

            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    synchronized (partitionCounts) {
                        partitionCounts.merge(metadata.partition(), 1, Integer::sum);
                    }
                }
            });
        }
        producer.flush();
        logger.info("Distribution across partitions: {}", partitionCounts);
        return partitionCounts;
    }

    public void demonstratePartitionOrdering() throws InterruptedException {
        String orderId = "order-789";
        String[] events = { "created", "validated", "paid", "shipped", "delivered" };

        for (String event : events) {
            ProducerRecord<String, String> record = new ProducerRecord<>("orders", orderId, event);

            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    logger.info("Event: {} -> Partition: {}, Offset: {}", event, metadata.partition(), metadata.offset());
                }
            });
            // small delay to demonstrate sequential processing
            Thread.sleep(100);
        }
        producer.flush();
    }

    public void demonstrateCrossPartitionBehavior() {
        long startTime = System.currentTimeMillis();

        // these will likely go to different partitions
        producer.send(new ProducerRecord<>("events", "key-A", "First at " + (System.currentTimeMillis() - startTime) + "ms"));
        producer.send(new ProducerRecord<>("events", "key-B", "Second at " + (System.currentTimeMillis() - startTime) + "ms"));
        producer.send(new ProducerRecord<>("events", "key-C", "Third at " + (System.currentTimeMillis() - startTime) + "ms"));

        producer.flush();
    }

    public void close() {
        if (producer != null) {
            producer.close();
        }
    }

    public void createConsumerGroup() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "order-processors");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("orders"));

        int recordCount = 0;
        while (recordCount < 10) { // process limited records for demo
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                logger.info("Consumer: {}, Partition: {}, Offset: {}, Value: {}", Thread.currentThread()
                    .getName(), record.partition(), record.offset(), record.value());
                recordCount++;
            }
            consumer.commitSync();
        }
        consumer.close();
    }

    public void startMultipleGroups() {
        String[] groupIds = { "analytics-group", "audit-group", "notification-group" };
        CountDownLatch latch = new CountDownLatch(groupIds.length);
        for (String groupId : groupIds) {
            startConsumerGroup(groupId, latch);
        }

        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
        }
    }

    private void startConsumerGroup(String groupId, CountDownLatch latch) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        new Thread(() -> {
            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
                consumer.subscribe(Arrays.asList("orders"));

                int recordCount = 0;
                while (recordCount < 5) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    recordCount += processRecordsForGroup(groupId, records);
                }
            } finally {
                latch.countDown();
            }
        }).start();
    }

    private int processRecordsForGroup(String groupId, ConsumerRecords<String, String> records) {
        int count = 0;
        for (ConsumerRecord<String, String> record : records) {
            logger.info("[{}] Processing: {}", groupId, record.value());
            count++;
        }
        return count;
    }

    public void configureCooperativeRebalancing() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "cooperative-group");
        props.put("partition.assignment.strategy", "org.apache.kafka.clients.consumer.CooperativeStickyAssignor");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("orders"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                logger.info("Revoked partitions: {}", partitions);
                // complete processing of current records
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                logger.info("Assigned partitions: {}", partitions);
                // initialize any partition-specific state
            }
        });

        // process a few records to demonstrate
        int recordCount = 0;
        while (recordCount < 5) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            recordCount += records.count();
        }

        consumer.close();
    }

    public void processWithManualCommit() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "manual-commit-group");
        props.put("enable.auto.commit", "false");
        props.put("max.poll.records", "10");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("orders"));

        int totalProcessed = 0;
        while (totalProcessed < 10) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                try {
                    processOrder(record);
                    totalProcessed++;
                } catch (Exception e) {
                    logger.error("Processing failed for offset: {}", record.offset(), e);
                    break;
                }
            }

            if (!records.isEmpty()) {
                consumer.commitSync();
                logger.info("Committed {} records", records.count());
            }
        }

        consumer.close();
    }

    private void processOrder(ConsumerRecord<String, String> record) {
        // simulate order processing
        logger.info("Processing order: {}", record.value());
        // this section is mostly your part of implementation, which is out of bounds of the article topic coverage
    }
}