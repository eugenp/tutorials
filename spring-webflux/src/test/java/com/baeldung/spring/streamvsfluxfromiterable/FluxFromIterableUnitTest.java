package com.baeldung.spring.streamvsfluxfromiterable;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

@SuppressWarnings("unchecked")
public class FluxFromIterableUnitTest {

    @Test
    void givenList_whenProcessedWithFlux_thenReturnDoubledEvenNumbers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        Flux<Integer> fluxPipeline = Flux.fromIterable(numbers)
          .filter(n -> n % 2 == 0)
          .map(n -> n * 2);

        StepVerifier.create(fluxPipeline)
          .expectNext(4, 16);
    }

    @Test
    void givenList_whenProcessingTakesLongerThanEmission_thenEmittedBeforeProcessing() {
        VirtualTimeScheduler.set(VirtualTimeScheduler.create());

        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        Flux<Integer> sourceFlux = Flux.fromIterable(numbers)
          .delayElements(Duration.ofMillis(500));

        Flux<Integer> processedFlux = sourceFlux.flatMap(n -> Flux.just(n * n)
          .delayElements(Duration.ofSeconds(1)));

        StepVerifier.withVirtualTime(() -> Flux.merge(sourceFlux, processedFlux))
          .expectSubscription()
          .expectNoEvent(Duration.ofMillis(500))
          .thenAwait(Duration.ofMillis(500 * 5))
          .expectNextCount(7)
          .thenAwait(Duration.ofMillis(5000))
          .expectNextCount(3)
          .verifyComplete();

    }

    @Test
    void givenList_whenDividedByZeroInFlux_thenReturnFallbackValue() {
        List<Integer> numbers = List.of(1, 2, 0, 4, 5);
        Flux<Integer> flux = Flux.fromIterable(numbers)
          .map(n -> 10 / n)
          .onErrorResume(e -> Flux.just(-1));

        StepVerifier.create(flux)
          .expectNext(10, 5, -1)
          .verifyComplete();
    }

    @Test
    void givenFlux_whenMultipleSubscribers_thenEachReceivesData() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        Flux<Integer> flux = Flux.fromIterable(numbers)
          .map(n -> n * 2);

        StepVerifier.create(flux)
          .expectNext(2, 4, 6, 8, 10)
          .verifyComplete();

        StepVerifier.create(flux)
          .expectNext(2, 4, 6, 8, 10)
          .verifyComplete();
    }

    @Test
    void givenLargeDataset_whenProcessedUsingStreamsAndFlux_shouldComparePerformance() {
        List<Integer> numbers = IntStream.rangeClosed(1, 1000000)
          .boxed()
          .toList();

        long streamTime = measureExecutionTime(() -> numbers.stream()
          .map(n -> n * 2)
          .toList());
        long fluxTime = measureExecutionTime(() -> Flux.fromIterable(numbers)
          .map(n -> n * 2)
          .collectList()
          .block());

        assertTrue(streamTime < fluxTime);
    }

    private long measureExecutionTime(Runnable task) {
        long start = System.nanoTime();
        task.run();
        return (System.nanoTime() - start) / 1_000_000;
    }

}
