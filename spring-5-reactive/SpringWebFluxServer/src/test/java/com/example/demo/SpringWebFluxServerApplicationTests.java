package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.service.NumberEventService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringWebFluxServerApplicationTests {

	@Autowired
	NumberEventService serv;

	
	@Test
	public void testIfEventsAreGenerted() {
		Flux<Integer> flux = serv.generateNumberEvent();
		StepVerifier.create(flux).expectSubscription().expectNextCount(1).expectNextCount(2).expectNextCount(3);
	}
	
	@Test
	public void testIfEventGenerationIsInfinite() {
		Flux<Integer> flux = serv.generateNumberEvent();
		assertThatExceptionOfType(AssertionError.class)
				.isThrownBy(() -> StepVerifier.create(flux).expectNextCount(1).expectComplete().verify())
				.withMessage("expectation \"expectComplete\" failed (expected: onComplete(); actual: onNext(1))");
	}
	
	@Test
	public void testEventForTimeDuration() {
		Flux<Integer> flux = serv.generateNumberEvent();
		StepVerifier.create(flux).expectSubscription().expectNextCount(1).expectNoEvent(Duration.ofMillis(001))
				.expectNextCount(2).expectNoEvent(Duration.ofMillis(100)).expectNextCount(3)
				.expectNoEvent(Duration.ofMillis(200)).expectNextCount(4).expectNoEvent(Duration.ofMillis(300))
				.expectNextCount(5).expectNoEvent(Duration.ofMillis(450)).expectNextCount(6)
				.expectNoEvent(Duration.ofMillis(700)).expectNextCount(7).expectNoEvent(Duration.ofMillis(900))
				.expectNextCount(8).expectNoEvent(Duration.ofMillis(999)).expectNextCount(8);
	}	
	
	
	
}
