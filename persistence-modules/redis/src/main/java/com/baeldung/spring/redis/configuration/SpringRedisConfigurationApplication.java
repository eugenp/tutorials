package com.baeldung.spring.redis.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRedisConfigurationApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisConfigurationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
