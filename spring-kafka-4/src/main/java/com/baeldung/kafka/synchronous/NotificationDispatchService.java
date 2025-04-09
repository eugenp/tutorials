package com.baeldung.kafka.synchronous;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@EnableConfigurationProperties(SynchronousKafkaProperties.class)
class NotificationDispatchService {

    private final SynchronousKafkaProperties synchronousKafkaProperties;
    private final ReplyingKafkaTemplate<String, NotificationDispatchRequest, NotificationDispatchResponse> replyingKafkaTemplate;

    NotificationDispatchService(SynchronousKafkaProperties synchronousKafkaProperties, ReplyingKafkaTemplate<String, NotificationDispatchRequest, NotificationDispatchResponse> replyingKafkaTemplate) {
        this.synchronousKafkaProperties = synchronousKafkaProperties;
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    NotificationDispatchResponse dispatch(NotificationDispatchRequest notificationDispatchRequest) throws ExecutionException, InterruptedException {
        String requestTopic = synchronousKafkaProperties.requestTopic();
        ProducerRecord<String, NotificationDispatchRequest> producerRecord = new ProducerRecord<>(requestTopic, notificationDispatchRequest);

        var requestReplyFuture = replyingKafkaTemplate.sendAndReceive(producerRecord);
        return requestReplyFuture.get().value();
    }

}