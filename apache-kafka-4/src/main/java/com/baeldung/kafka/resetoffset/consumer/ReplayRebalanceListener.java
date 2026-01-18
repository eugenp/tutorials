package com.baeldung.kafka.resetoffset.consumer;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReplayRebalanceListener implements ConsumerRebalanceListener {

    private final KafkaConsumer<String, String> consumer;
    private final boolean replayEnabled;
    private final long replayFromTimestamp;

    private boolean seekDone = false;

    public ReplayRebalanceListener(KafkaConsumer<String, String> consumer, boolean replayEnabled, long replayFromTimestamp) {

        this.consumer = consumer;
        this.replayEnabled = replayEnabled;
        this.replayFromTimestamp = replayFromTimestamp;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        consumer.commitSync();
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        if (!replayEnabled || seekDone || partitions.isEmpty()) {
            return;
        }

        Map<TopicPartition, Long> partitionsTimestamp = partitions.stream()
            .collect(Collectors.toMap(Function.identity(), tp -> replayFromTimestamp));

        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(partitionsTimestamp);

        partitions.forEach(tp -> {
            OffsetAndTimestamp offsetAndTimestamp = offsets.get(tp);

            if (offsetAndTimestamp != null) {
                consumer.seek(tp, offsetAndTimestamp.offset());
            }
        });

        seekDone = true;
    }
}
