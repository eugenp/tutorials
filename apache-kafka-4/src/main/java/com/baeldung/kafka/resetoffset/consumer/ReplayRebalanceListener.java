package com.baeldung.kafka.resetoffset.consumer;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReplayRebalanceListener implements ConsumerRebalanceListener {

    private final KafkaConsumer<String, String> consumer;
    private final long replayFromTimeInEpoch;
    private final AtomicBoolean seekDone = new AtomicBoolean(false);

    public ReplayRebalanceListener(KafkaConsumer<String, String> consumer, long replayFromTimeInEpoch) {
        this.consumer = consumer;
        this.replayFromTimeInEpoch = replayFromTimeInEpoch;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        consumer.commitSync();
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        if (seekDone.get() || partitions.isEmpty()) {
            return;
        }

        Map<TopicPartition, Long> partitionsTimestamp = partitions.stream()
            .collect(Collectors.toMap(Function.identity(), tp -> replayFromTimeInEpoch));

        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(partitionsTimestamp);

        partitions.forEach(tp -> {
            OffsetAndTimestamp offsetAndTimestamp = offsets.get(tp);
            if (offsetAndTimestamp != null) {
                consumer.seek(tp, offsetAndTimestamp.offset());
            }
        });

        seekDone.set(true);
    }
}
