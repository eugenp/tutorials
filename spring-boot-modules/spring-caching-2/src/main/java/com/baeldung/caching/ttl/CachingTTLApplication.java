package com.baeldung.caching.ttl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CachingTTLApplication {
  public static void main(String[] args) {
    SpringApplication.run(CachingTTLApplication.class, args);
  }
}