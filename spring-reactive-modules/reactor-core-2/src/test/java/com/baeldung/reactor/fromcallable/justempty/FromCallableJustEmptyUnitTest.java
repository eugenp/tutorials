package com.baeldung.reactor.fromcallable.justempty;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class FromCallableJustEmptyUnitTest {

    @Test
    public void givenDataAvailable_whenCallingFromCallable_thenLazyEvaluation() {
        AtomicLong timeTakenForCompletion = new AtomicLong();
        Mono<String> dataFetched = Mono.fromCallable(this::fetchData)
            .doOnSubscribe(subscription -> timeTakenForCompletion.set(-1 * System.nanoTime()))
            .doFinally(consumer -> timeTakenForCompletion.addAndGet(System.nanoTime()));

        StepVerifier.create(dataFetched)
            .expectNext("Data Fetched")
            .verifyComplete();

        log.debug("Time Taken to Retrieve Data with Lazy Execution with Subscription");
        assertThat(TimeUnit.NANOSECONDS.toMillis(timeTakenForCompletion.get())).isCloseTo(5000L, Offset.offset(50L));
    }

    @Test
    public void givenExceptionThrown_whenCallingFromCallable_thenFromCallableCapturesError() {
        Mono<String> dataFetched = Mono.fromCallable(() -> {
                String data = fetchData();
                if (data.equals("Data Fetched")) {
                    throw new RuntimeException("ERROR");
                }
                return data;
            })
            .onErrorResume(error -> Mono.just("COMPLETED"));

        StepVerifier.create(dataFetched)
            .expectNext("COMPLETED")
            .verifyComplete();
    }

    @Test
    public void givenDataAvailable_whenCallingJustOrEmpty_thenEagerEvaluation() {
        AtomicLong timeTakenToReceiveOnCompleteSignalAfterSubscription = new AtomicLong();
        AtomicLong timeTakenForMethodCompletion = new AtomicLong(-1 * System.nanoTime());
        Mono<String> dataFetched = Mono.justOrEmpty(fetchData())
            .doOnSubscribe(subscription -> timeTakenToReceiveOnCompleteSignalAfterSubscription.set(-1 * System.nanoTime()))
            .doFinally(consumer -> timeTakenToReceiveOnCompleteSignalAfterSubscription.addAndGet(System.nanoTime()));

        timeTakenForMethodCompletion.addAndGet(System.nanoTime());

        StepVerifier.create(dataFetched)
            .expectNext("Data Fetched")
            .verifyComplete();

        assertThat(TimeUnit.NANOSECONDS.toMillis(timeTakenToReceiveOnCompleteSignalAfterSubscription.get())).isCloseTo(1L, Offset.offset(1L));
        assertThat(TimeUnit.NANOSECONDS.toMillis(timeTakenForMethodCompletion.get())).isCloseTo(5000L, Offset.offset(50L));
    }

    @Test
    public void givenLatestStatusIsEmpty_thenCallingFromCallableForEagerEvaluation() {
        Optional<String> latestStatus = fetchLatestStatus();
        String updatedStatus = "ACTIVE";
        Mono<String> currentStatus = Mono.justOrEmpty(latestStatus)
            .switchIfEmpty(Mono.fromCallable(() -> updatedStatus));

        StepVerifier.create(currentStatus)
            .expectNext(updatedStatus)
            .verifyComplete();
    }

    private Optional<String> fetchLatestStatus() {
        List<String> activeStatusList = List.of("ARCHIVED", "ACTIVE");
        if (activeStatusList.contains("ARCHIVED")) {
            return Optional.empty();
        }
        return Optional.of(activeStatusList.get(0));
    }

    private String fetchData() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Data Fetched";
    }
}
