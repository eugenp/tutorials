package com.baeldung.kafka.resetoffset.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.baeldung.kafka.resetoffset.consumer.KafkaConsumerService;

public class ResetOffsetService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final AdminClient adminClient;

    public ResetOffsetService(String bootstrapServers) {
        this.adminClient = AdminClient.create(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers));
    }

    public void reset(String topic, String consumerGroup) {
        List<TopicPartition> partitions;
        try {
            partitions = fetchPartitions(topic);
        } catch (ExecutionException | InterruptedException ex) {
            log.error("Error in the fetching partitions with exception {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }

        Map<TopicPartition, OffsetAndMetadata> earliestOffsets = fetchEarliestOffsets(partitions);

        try {
            adminClient.alterConsumerGroupOffsets(consumerGroup, earliestOffsets)
                .all()
                .get();
        } catch (InterruptedException | ExecutionException ex) {
            log.error("Error in the Kafka Consumer reset with exception {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    private List<TopicPartition> fetchPartitions(String topic) throws ExecutionException, InterruptedException {
        return adminClient.describeTopics(List.of(topic))
            .values()
            .get(topic)
            .get()
            .partitions()
            .stream()
            .map(p -> new TopicPartition(topic, p.partition()))
            .toList();
    }

    private Map<TopicPartition, OffsetAndMetadata> fetchEarliestOffsets(List<TopicPartition> partitions) {
        Map<TopicPartition, OffsetSpec> offsetSpecs = partitions.stream()
            .collect(Collectors.toMap(tp -> tp, tp -> OffsetSpec.earliest()));

        ListOffsetsResult offsetsResult = adminClient.listOffsets(offsetSpecs);
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();

        for (var tp : partitions) {
            long offset;
            try {
                offset = offsetsResult.partitionResult(tp)
                    .get()
                    .offset();
            } catch (InterruptedException | ExecutionException ex) {
                log.error("Error in the Kafka Consumer reset with exception {}", ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }
            offsets.put(tp, new OffsetAndMetadata(offset));
        }

        return offsets;
    }

    public void close() {
        adminClient.close();
    }
}
