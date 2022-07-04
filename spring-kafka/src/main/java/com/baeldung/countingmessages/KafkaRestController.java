package com.baeldung.countingmessages;

import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baeldung.countingmessages.KafkaConsumerConfig.props;

@RestController
public class KafkaRestController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    private static long producerNumberOfMessages = 0;

    @PostMapping("{topic}/send-message")
    public void sendMessage(@PathVariable("topic") String topic, @RequestBody String message){
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
                producerNumberOfMessages = result.getRecordMetadata().offset() + 1;
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });

    }

    @GetMapping("v1/consumer/number-of-messages/{topic}")
    public Long getTotalNumberOfMessagesFromConsumer(@PathVariable("topic") String topic){
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(props);
        List<TopicPartition> partitions = consumer.partitionsFor(topic).stream()
                .map(p -> new TopicPartition(topic, p.partition()))
                .collect(Collectors.toList());
        consumer.assign(partitions);
        consumer.seekToEnd(Collections.emptySet());
        Map<TopicPartition, Long> endPartitions = partitions.stream()
                .collect(Collectors.toMap(Function.identity(), consumer::position));
        consumer.seekToBeginning(Collections.emptySet());
        return partitions.stream().mapToLong(p -> endPartitions.get(p) - consumer.position(p)).sum();
    }

    @GetMapping("v2/consumer/number-of-messages")
    public Long getTotalNumberOfMessagesFromConsumer(){
        return KafkaConsumer.getNumberOfMessages();
    }

    @GetMapping("producer/number-of-messages")
    public Long getTotalNumberOfMessagesFromProducer(){
        return producerNumberOfMessages;
    }
}
