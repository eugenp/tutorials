package com.baeldung.boot.embeddedRedis;

import java.io.IOException;

import org.springframework.boot.test.context.TestConfiguration;

import com.baeldung.boot.embeddedRedis.configuration.RedisProperties;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfiguration {

    private final RedisServer redisServer;

    public TestRedisConfiguration(final RedisProperties redisProperties) throws IOException {
        this.redisServer = new RedisServer(redisProperties.getRedisPort());
        //Uncomment below if running on windows and can't start redis server
//        this.redisServer = RedisServer.builder().setting("maxheap 200m").port(6379).setting("bind localhost").build();
//        redisServer.start();
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        redisServer.stop();
    }
}
