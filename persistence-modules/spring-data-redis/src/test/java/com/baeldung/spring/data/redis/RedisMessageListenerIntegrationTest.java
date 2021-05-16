package com.baeldung.spring.data.redis;

import com.baeldung.spring.data.redis.queue.RedisMessagePublisher;
import com.baeldung.spring.data.redis.queue.RedisMessageSubscriber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import redis.embedded.RedisServerBuilder;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringRedisApplication.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class RedisMessageListenerIntegrationTest {

    private static redis.embedded.RedisServer redisServer;

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

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
    public void testOnMessage() throws Exception {
        String message = "Message " + UUID.randomUUID();
        redisMessagePublisher.publish(message);
        Thread.sleep(1000);
        assertTrue(RedisMessageSubscriber.messageList.get(0).contains(message));
    }
}