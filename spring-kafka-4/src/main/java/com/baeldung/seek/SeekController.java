package com.baeldung.seek;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seek/api/v1/")
public class SeekController {

    public static final String TOPIC_NAME = "test-topic";

    private final DefaultKafkaConsumerFactory<String, String> consumerFactory;

    public SeekController(DefaultKafkaConsumerFactory<String, String> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    @GetMapping("partition/{partition}/offset/{offset}")
    public ResponseEntity<Response> getOneByPartitionAndOffset(@PathVariable("partition") int partition, @PathVariable("offset") int offset) {
        try (KafkaConsumer<String, String> consumer = (KafkaConsumer<String, String>) consumerFactory.createConsumer()) {
            TopicPartition topicPartition = new TopicPartition(TOPIC_NAME, partition);
            consumer.assign(Collections.singletonList(topicPartition));
            consumer.seek(topicPartition, offset);
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            Iterator<ConsumerRecord<String, String>> recordIterator = records.iterator();
            if (recordIterator.hasNext()) {
                ConsumerRecord<String, String> consumerRecord = recordIterator.next();
                Response response = new Response(consumerRecord.partition(), consumerRecord.offset(), consumerRecord.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("partition/{partition}/beginning")
    public ResponseEntity<Response> getOneByPartitionToBeginningOffset(@PathVariable("partition") int partition) {
        try (KafkaConsumer<String, String> consumer = (KafkaConsumer<String, String>) consumerFactory.createConsumer()) {
            TopicPartition topicPartition = new TopicPartition(TOPIC_NAME, partition);
            consumer.assign(Collections.singletonList(topicPartition));
            consumer.seekToBeginning(Collections.singleton(topicPartition));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            Iterator<ConsumerRecord<String, String>> recordIterator = records.iterator();
            if (recordIterator.hasNext()) {
                ConsumerRecord<String, String> consumerRecord = recordIterator.next();
                Response response = new Response(consumerRecord.partition(), consumerRecord.offset(), consumerRecord.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("partition/{partition}/end")
    public ResponseEntity<Long> getOneByPartitionToEndOffset(@PathVariable("partition") int partition) {
        try (KafkaConsumer<String, String> consumer = (KafkaConsumer<String, String>) consumerFactory.createConsumer()) {
            TopicPartition topicPartition = new TopicPartition(TOPIC_NAME, partition);
            consumer.assign(Collections.singletonList(topicPartition));
            consumer.seekToEnd(Collections.singleton(topicPartition));
            return new ResponseEntity<>(consumer.position(topicPartition), HttpStatus.OK);
        }
    }

}
