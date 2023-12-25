package com.baeldung.spring.kafka.deserialization.exception;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

class KafkaErrorHandler implements CommonErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(KafkaErrorHandler.class);

    @Override
    public void handleRecord(Exception exception, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        handle(exception, consumer);
    }

    @Override
    public void handleOtherException(Exception exception, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        handle(exception, consumer);
    }

    private void handle(Exception ex, Consumer<?, ?> consumer) {
        log.error("Exception thrown", ex);
        if (ex instanceof RecordDeserializationException) {
            RecordDeserializationException rde = (RecordDeserializationException) ex;
            consumer.seek(rde.topicPartition(), rde.offset() + 1L);
            consumer.commitSync();
        } else {
            log.error("Exception not handled");
        }
    }
}