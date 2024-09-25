package com.baeldung.spring.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class KafkaApplicationLongMessage {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplicationLongMessage.class, args);

        LongMessageProducer producer = context.getBean(LongMessageProducer.class);

        String fileData = readLongMessage();
        producer.sendMessage(fileData);

        //Deliberate delay to let listener consume produced message before main thread stops
        Thread.sleep(5000);
        context.close();
    }

    private static String readLongMessage() throws IOException {
        String data = "";

        //update complete location of large message here
        data = new String(Files.readAllBytes(Paths.get("RandomTextFile.txt")));
        return data;
    }

    @Bean
    public LongMessageProducer longMessageProducer() {
        return new LongMessageProducer();
    }

    @Bean
    public LongMessageListener longMessageListener() {
        return new LongMessageListener();
    }

    public static class LongMessageProducer {

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        @Value(value = "${long.message.topic.name}")
        private String topicName;

        public void sendMessage(String message) {
            kafkaTemplate.send(topicName, message);
            System.out.println("Long message Sent");
        }

    }

    public static class LongMessageListener {

        @KafkaListener(topics = "${long.message.topic.name}", groupId = "longMessage", containerFactory = "longMessageKafkaListenerContainerFactory")
        public void listenGroupLongMessage(String message) {
            System.out.println("Received Message in group 'longMessage'");
        }

    }

}
