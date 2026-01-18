package com.baeldung.kafka.resetoffset.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class OffsetResetService {
    private final AdminClient adminClient;

    public OffsetResetService(String bootstrapServers) {
        this.adminClient = AdminClient.create(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers));
    }

    public void reset(String topic, String consumerGroup) throws ExecutionException, InterruptedException {
        List<TopicPartition> partitions = fetchPartitions(topic);

        Map<TopicPartition, OffsetAndMetadata> earliestOffsets = fetchEarliestOffsets(partitions);

        adminClient.alterConsumerGroupOffsets(consumerGroup, earliestOffsets)
            .all()
            .get();
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
        Map<TopicPartition,OffsetSpec> offsetSpecs = partitions.stream()
            .collect(Collectors.toMap(tp -> tp, tp -> OffsetSpec.earliest()));

        ListOffsetsResult offsetsResult = adminClient.listOffsets(offsetSpecs);
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();

        for (var tp : partitions) {
            long offset;
            try {
                offset = offsetsResult.partitionResult(tp).get().offset();
            } catch (InterruptedException | ExecutionException ex) {
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
