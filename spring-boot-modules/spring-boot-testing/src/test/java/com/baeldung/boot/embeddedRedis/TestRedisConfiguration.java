package com.baeldung.boot.embeddedRedis;

import com.baeldung.boot.embeddedRedis.configuration.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class TestRedisConfiguration {

    private final RedisServer redisServer;

    public TestRedisConfiguration(final RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getRedisPort());
        //Uncomment below if running on windows and can't start redis server
//        this.redisServer = RedisServer.builder().setting("maxheap 200m").port(6379).setting("bind localhost").build();
//        redisServer.start();
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
