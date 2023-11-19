package com.baeldung.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
public class Spring5ReactiveApplication{

    public static void main(String[] args) {
        SpringApplication.run(Spring5ReactiveApplication.class, args);
    }

}
