package com.baelding.springbootredis.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

@TestConfiguration
public class RedisTestConfiguration {

    private final RedisServer redisServer;

    public RedisTestConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getPort());
        // To run on windows uncomment the following lines
        // this.redisServer = RedisServer.builder().setting("maxheap 200m")
        //         .port(redisProperties.getPort())
        //         .setting("bind localhost")
        //         .build();
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
