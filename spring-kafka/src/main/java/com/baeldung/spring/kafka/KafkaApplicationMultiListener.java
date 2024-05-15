package com.baeldung.spring.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaApplicationMultiListener {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplicationMultiListener.class, args);

        MessageProducer producer = context.getBean(MessageProducer.class);
        producer.sendMessages();

        Thread.sleep(5000);
        context.close();
    }

    @Bean
    public MessageProducer MessageProducer() {
        return new MessageProducer();
    }

    public static class MessageProducer {

        @Autowired
        private KafkaTemplate<String, Object> multiTypeKafkaTemplate;

        @Value(value = "${multi.type.topic.name}")
        private String multiTypeTopicName;

        public void sendMessages() {
            multiTypeKafkaTemplate.send(multiTypeTopicName, new Greeting("Greetings", "World!"));
            multiTypeKafkaTemplate.send(multiTypeTopicName, new Farewell("Farewell", 25));
            multiTypeKafkaTemplate.send(multiTypeTopicName, "Simple string message");
        }

    }

}
