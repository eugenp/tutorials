package com.baeldung.reactive.redis.template;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.reactive.redis.SpringRedisReactiveApplication;
import com.baeldung.reactive.redis.model.Employee;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringRedisReactiveApplication.class)
public class RedisTemplateValueOpsIntegrationTest {

    @Autowired
    private ReactiveRedisTemplate<String, Employee> redisTemplate;

    @Test
    public void givenEmployee_whenSet_thenSet() {

        Mono<Boolean> result = redisTemplate.opsForValue()
            .set("123", new Employee("123", "Bill", "Accounts"));

        StepVerifier.create(result)
            .expectNext(true)
            .verifyComplete();
    }

    @Test
    public void givenEmployeeId_whenGet_thenReturnsEmployee() {

        Mono<Employee> fetchedEmployee = redisTemplate.opsForValue()
            .get("123");

        StepVerifier.create(fetchedEmployee)
            .expectNext(new Employee("123", "Bill", "Accounts"))
            .verifyComplete();
    }

    @Test
    public void givenEmployee_whenSetWithExpiry_thenSetsWithExpiryTime() throws InterruptedException {

        Mono<Boolean> result = redisTemplate.opsForValue()
            .set("129", new Employee("129", "John", "Programming"), Duration.ofSeconds(1));

        Mono<Employee> fetchedEmployee = redisTemplate.opsForValue()
            .get("129");

        StepVerifier.create(result)
            .expectNext(true)
            .verifyComplete();

        Thread.sleep(2000L);

        StepVerifier.create(fetchedEmployee)
            .expectNextCount(0L)
            .verifyComplete();
    }

}
