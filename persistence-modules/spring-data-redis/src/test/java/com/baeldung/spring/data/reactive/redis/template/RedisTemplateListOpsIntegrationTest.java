package com.baeldung.spring.data.reactive.redis.template;


import com.baeldung.spring.data.reactive.redis.SpringRedisReactiveApplication;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import redis.embedded.RedisServerBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringRedisReactiveApplication.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class RedisTemplateListOpsIntegrationTest {

    private static final String LIST_NAME = "demo_list";
    private static redis.embedded.RedisServer redisServer;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    private ReactiveListOperations<String, String> reactiveListOps;
    
    @BeforeClass
    public static void startRedisServer() throws IOException {
        redisServer = new RedisServerBuilder().port(6379).setting("maxmemory 128M").build();
        redisServer.start();
    }
    
    @AfterClass
    public static void stopRedisServer() throws IOException {
        redisServer.stop();
    }

    @Before
    public void setup() {
        reactiveListOps = redisTemplate.opsForList();
    }

    @Test
    public void givenListAndValues_whenLeftPushAndLeftPop_thenLeftPushAndLeftPop() {
        Mono<Long> lPush = reactiveListOps.leftPushAll(LIST_NAME, "first", "second")
            .log("Pushed");

        StepVerifier.create(lPush)
            .expectNext(2L)
            .verifyComplete();

        Mono<String> lPop = reactiveListOps.leftPop(LIST_NAME)
            .log("Popped");

        StepVerifier.create(lPop)
            .expectNext("second")
            .verifyComplete();
    }

}
