package com.baeldung;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.baeldung.spring.data.redis.SpringRedisApplication;

import redis.embedded.RedisServerBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringRedisApplication.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class SpringContextTest {
    
    private static redis.embedded.RedisServer redisServer;
    
    @BeforeClass
    public static void startRedisServer() {
        redisServer = new RedisServerBuilder().port(6379).setting("maxmemory 256M").build();
        redisServer.start();
    }

    @AfterClass
    public static void stopRedisServer() {
        redisServer.stop();
    }
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
