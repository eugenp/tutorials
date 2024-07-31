package com.baeldung.mono;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@Slf4j
public class MonoUnitTest {
    @Test
    public void whenMonoProducesString_thenBlockAndConsume() {

        String result1 = blockingHelloWorld().block();
        assertEquals("Hello world!", result1);

        String result2 = blockingHelloWorld()
                .block(Duration.of(1000, ChronoUnit.MILLIS));
        assertEquals("Hello world!", result2);

        Optional<String> result3 = Mono.<String>empty().blockOptional();
        assertEquals(Optional.empty(), result3);
    }

    @Test
    public void whenMonoProducesString_thenConsumeNonBlocking() {

        blockingHelloWorld()
                .doOnNext(result -> assertEquals("Hello world!", result))
                .subscribe();

        blockingHelloWorld()
                .subscribe(result -> assertEquals("Hello world!", result));

    }

    private Mono<String> blockingHelloWorld() {
        // blocking
        return Mono.just("Hello world!");
    }

    @Test
    public void whenMonoProducesListOfElements_thenConvertToFluxofElements() {

        Mono<List<String>> monoList = monoOfList();

        StepVerifier.create(monoTofluxUsingFlatMapIterable(monoList))
                .expectNext("one", "two", "three", "four")
                .verifyComplete();

        StepVerifier.create(monoTofluxUsingFlatMapMany(monoList))
                .expectNext("one", "two", "three", "four")
                .verifyComplete();
    }

    private <T> Flux<T> monoTofluxUsingFlatMapIterable(Mono<List<T>> monoList) {
        return monoList
                .flatMapIterable(list -> list)
                .log();
    }

    private <T> Flux<T> monoTofluxUsingFlatMapMany(Mono<List<T>> monoList) {
        return monoList
                .flatMapMany(Flux::fromIterable)
                .log();
    }

    private Mono<List<String>> monoOfList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");

        return Mono.just(list);
    }

    @Test
    public void whenEmptyList_thenMonoDeferExecuted() {

        Mono<List<String>> emptyList = Mono.defer(() -> monoOfEmptyList());

        //Empty list, hence Mono publisher in switchIfEmpty executed after condition evaluation
        Flux<String> emptyListElements = emptyList.flatMapIterable(l -> l)
          .switchIfEmpty(Mono.defer(() -> sampleMsg("EmptyList")))
          .log();

        StepVerifier.create(emptyListElements)
          .expectNext("EmptyList")
          .verifyComplete();
    }

    @Test
    public void whenNonEmptyList_thenMonoDeferNotExecuted() {

        Mono<List<String>> nonEmptyist = Mono.defer(() -> monoOfList());

        //Non empty list, hence Mono publisher in switchIfEmpty won't evaluated.
        Flux<String> listElements = nonEmptyist.flatMapIterable(l -> l)
          .switchIfEmpty(Mono.defer(() -> sampleMsg("NonEmptyList")))
          .log();

        StepVerifier.create(listElements)
          .expectNext("one", "two", "three", "four")
          .verifyComplete();
    }

    private Mono<List<String>> monoOfEmptyList() {
        List<String> list = new ArrayList<>();
        return Mono.just(list);
    }

    @Test
    public void whenUsingMonoJust_thenEagerEvaluation() throws InterruptedException {

        Mono<String> msg = sampleMsg("Eager Publisher");

        log.debug("Intermediate Test Message....");

        StepVerifier.create(msg)
          .expectNext("Eager Publisher")
          .verifyComplete();

        Thread.sleep(5000);

        StepVerifier.create(msg)
          .expectNext("Eager Publisher")
          .verifyComplete();
    }

    @Test
    public void whenUsingMonoDefer_thenLazyEvaluation() throws InterruptedException {

        Mono<String> deferMsg = Mono.defer(() -> sampleMsg("Lazy Publisher"));

        log.debug("Intermediate Test Message....");

        StepVerifier.create(deferMsg)
          .expectNext("Lazy Publisher")
          .verifyComplete();

        Thread.sleep(5000);

        StepVerifier.create(deferMsg)
          .expectNext("Lazy Publisher")
          .verifyComplete();

    }

    private Mono<String> sampleMsg(String str) {
        log.debug("Call to Retrieve Sample Message!! --> {} at: {}", str, System.currentTimeMillis());
        return Mono.just(str);
    }
}



