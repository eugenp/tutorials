package com.baeldung.spring.kafka.delay;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.listener.KafkaBackoffException;
import org.springframework.kafka.listener.KafkaConsumerBackoffManager;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.adapter.AbstractDelegatingMessageListenerAdapter;
import org.springframework.kafka.support.Acknowledgment;

public class DelayedMessageListenerAdapter<K, V> extends AbstractDelegatingMessageListenerAdapter<MessageListener<K, V>>
    implements AcknowledgingConsumerAwareMessageListener<K, V> {

    private static final Duration DEFAULT_DELAY_VALUE = Duration.of(0, ChronoUnit.SECONDS);

    private final String listenerId;

    private final KafkaConsumerBackoffManager kafkaConsumerBackoffManager;

    private final Map<String, Duration> delaysPerTopic = new ConcurrentHashMap<>();

    private Duration defaultDelay = DEFAULT_DELAY_VALUE;

    public DelayedMessageListenerAdapter(MessageListener<K, V> delegate, KafkaConsumerBackoffManager kafkaConsumerBackoffManager, String listenerId) {
        super(delegate);
        Objects.requireNonNull(kafkaConsumerBackoffManager, "kafkaConsumerBackoffManager cannot be null");
        Objects.requireNonNull(listenerId, "listenerId cannot be null");
        this.kafkaConsumerBackoffManager = kafkaConsumerBackoffManager;
        this.listenerId = listenerId;
    }

    @Override
    public void onMessage(ConsumerRecord<K, V> consumerRecord, Acknowledgment acknowledgment, Consumer<?, ?> consumer) throws KafkaBackoffException {
        this.kafkaConsumerBackoffManager.backOffIfNecessary(createContext(consumerRecord, consumerRecord.timestamp() +
            delaysPerTopic.getOrDefault(consumerRecord.topic(), this.defaultDelay)
                .toMillis(), consumer));
        invokeDelegateOnMessage(consumerRecord, acknowledgment, consumer);
    }

    public void setDelayForTopic(String topic, Duration delay) {
        Objects.requireNonNull(topic, "Topic cannot be null");
        Objects.requireNonNull(delay, "Delay cannot be null");
        this.logger.debug(() -> String.format("Setting %s seconds delay for topic %s", delay, topic));
        this.delaysPerTopic.put(topic, delay);
    }

    public void setDefaultDelay(Duration delay) {
        Objects.requireNonNull(delay, "Delay cannot be null");
        this.logger.debug(() -> String.format("Setting %s seconds delay for listener id %s", delay, this.listenerId));
        this.defaultDelay = delay;
    }

    private void invokeDelegateOnMessage(ConsumerRecord<K, V> consumerRecord, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        switch (this.delegateType) {
            case ACKNOWLEDGING_CONSUMER_AWARE:
                this.delegate.onMessage(consumerRecord, acknowledgment, consumer);
                break;
            case ACKNOWLEDGING:
                this.delegate.onMessage(consumerRecord, acknowledgment);
                break;
            case CONSUMER_AWARE:
                this.delegate.onMessage(consumerRecord, consumer);
                break;
            case SIMPLE:
                this.delegate.onMessage(consumerRecord);
        }
    }

    private KafkaConsumerBackoffManager.Context createContext(ConsumerRecord<K, V> data, long nextExecutionTimestamp, Consumer<?, ?> consumer) {
        return this.kafkaConsumerBackoffManager.createContext(nextExecutionTimestamp, this.listenerId, new TopicPartition(data.topic(), data.partition()),
            consumer);
    }

    @Override
    public void onMessage(ConsumerRecord<K, V> data) {
        onMessage(data, null, null);
    }

    @Override
    public void onMessage(ConsumerRecord<K, V> data, Acknowledgment acknowledgment) {
        onMessage(data, acknowledgment, null);
    }

    @Override
    public void onMessage(ConsumerRecord<K, V> data, Consumer<?, ?> consumer) {
        onMessage(data, null, consumer);
    }
}