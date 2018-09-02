package com.baeldung.reactive.redis.template;

import com.baeldung.reactive.redis.SpringRedisReactiveApplication;
import com.baeldung.reactive.redis.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringRedisReactiveApplication.class)
public class RedisTemplateListOpsIntegrationTest {

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    private ReactiveListOperations<String, String> reactiveListOps;

    private static final String LIST_NAME = "demo_list";

    @Before
    public void setup() {
        reactiveListOps = redisTemplate.opsForList();
    }

    @Test
    public void givenListAndValues_whenBlockingLeftPushAndRightPop_thenLeftPushAndRightPop() {
        Mono<Long> blockingPush = reactiveListOps.leftPushAll(LIST_NAME, "first", "second")
            .log("Pushed");

        StepVerifier.create(blockingPush)
            .expectNext(2L)
            .verifyComplete();

        Mono<String> blockingPop = reactiveListOps.leftPop(LIST_NAME)
            .log("Popped");

        StepVerifier.create(blockingPop)
            .expectNext("second")
            .verifyComplete();
    }

}
