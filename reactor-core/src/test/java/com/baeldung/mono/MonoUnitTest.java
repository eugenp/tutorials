package com.baeldung.mono;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
                .flatMapMany(it -> Flux.fromIterable(it))
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

    @Test
    public void testFluxDelay(){

        Flux test = Flux.range(1,10)
          .publishOn(Schedulers.parallel())
          .log()
          .doOnNext(n -> log.debug("first... {}",n))
          .limitRate(4)
          .doOnNext(n -> log.debug("second... {}",n))
//          .map(x -> {
//              try {
//                  Thread.sleep(2000L);
//              } catch (InterruptedException e) {
//                  e.printStackTrace();
//              }
//              return x;
//          });
//          .flatMap(i -> )
//          .delayElements(Duration.ofMillis(3000L), Schedulers.parallel())
          .doOnNext(n -> log.debug("third... {}",n));
//          .delaySubscription(Duration.ofMillis(5000L));
//          .delaySubscription(Flux.just(1,2,3).doOnNext(n -> log.debug("second {}",n)).log());

        test.toStream().forEach(n -> log.debug("stream....{}",n));

//        StepVerifier.create(test).expectNext(1,2,3,4,5,6,7,8,9,10).verifyComplete();

    }

    @Test
    public void delayedFlux(){
//        Flux delayedFlux  = Flux.interval(Duration.ofSeconds(1))
        Flux delayedFlux  = Flux.range(1,100)
          .log()
          .limitRate(4)
          .log()
          .map(n -> {
              try {
                  Thread.sleep(1000L);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              return n;
          })
//          .delayElements(Duration.ofMillis(1000L))
          .doOnNext(n -> log.debug("First...{}",n));


          delayedFlux.toStream().forEach(x -> log.debug("final...{}",x));
    }
}



