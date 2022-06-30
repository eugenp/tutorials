package com.baeldung.countingmessages;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
public class KafkaConsumer {
    private static long numberOfMessages = 0;
    @KafkaListener(topicPartitions =
            { @TopicPartition(topic = "${message.topic.name}",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))},
            topics = "${message.topic.name}", groupId = "foo", containerFactory = "fooKafkaListenerContainerFactory")
    public void consume(String message) {
        numberOfMessages++;
        System.out.println("message = " + message);
    }

    public static Long getNumberOfMessages(){
        return numberOfMessages;
    }


}
