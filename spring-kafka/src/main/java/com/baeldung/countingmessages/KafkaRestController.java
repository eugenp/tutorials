package com.baeldung.countingmessages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("consumer/number-of-messages")
    public Long getTotalNumberOfMessagesFromConsumer(){
        return KafkaConsumer.getNumberOfMessages();
    }

    @GetMapping("producer/number-of-messages")
    public Long getTotalNumberOfMessagesFromProducer(){
        return producerNumberOfMessages;
    }
}
