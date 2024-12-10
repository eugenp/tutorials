package com.baeldung.reactive.kafka.stream.binder.kafka.consumer;

import com.baeldung.reactive.kafka.stream.binder.domain.StockUpdate;
import com.baeldung.reactive.kafka.stream.binder.kafka.TopicConfig;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
@Slf4j
public class StockPriceConsumer {

    private final ReactiveKafkaConsumerTemplate<String, StockUpdate> kafkaConsumerTemplate;
    private final AtomicInteger count = new AtomicInteger();

    @SuppressWarnings("all")
    public StockPriceConsumer(@NonNull KafkaProperties properties, @Qualifier(TopicConfig.STOCK_PRICES_OUT) NewTopic topic) {
        var receiverOptions = ReceiverOptions.<String, StockUpdate>create(properties.buildConsumerProperties())
                .subscription(List.of(topic.name()))
                .assignment(IntStream.range(0, topic.numPartitions()).mapToObj(i -> new TopicPartition(topic.name(), i)).toList())
                .addAssignListener(partitions -> log.info("************** onPartitionsAssigned: {}", partitions));

        this.kafkaConsumerTemplate = new ReactiveKafkaConsumerTemplate<>(receiverOptions);;
    }

    @PostConstruct
    public void consume() {
      Schedulers.boundedElastic().schedule(() -> kafkaConsumerTemplate
              .receiveAutoAck()
              .doOnNext(consumerRecord -> {
                  // simulate processing
                  count.incrementAndGet();

                  log.info(
                          "received key={}, value={} from topic={}, offset={}, partition={}", consumerRecord.key(),
                          consumerRecord.value(),
                          consumerRecord.topic(),
                          consumerRecord.offset(),
                          consumerRecord.partition());
              })
              .doOnError(e -> log.error("Consumer error",  e))
              .doOnComplete(() -> log.info("Consumed all messages"))
              .subscribe());
    }

    public int getCount() {
        return count.get();
    }

    public void resetCount() {
        count.set(0);
    }

}