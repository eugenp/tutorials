package com.baeldung.spring.kafka.delay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaDelayApplication {
  public static void main(String[] args) {
    SpringApplication.run(KafkaDelayApplication.class, args);
  }
}
