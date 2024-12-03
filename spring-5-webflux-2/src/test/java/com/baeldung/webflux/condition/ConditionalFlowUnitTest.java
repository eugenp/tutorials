package com.baeldung.webflux.condition;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ConditionalFlowUnitTest {

    @Test
    public void givenNumbersFlux_whenMappedToOddOrEven_thenReturnCorrectLabels() {
        Flux<String> oddEvenFlux = Flux.just(1, 2, 3, 4, 5, 6)
            .map(num -> {
                if (num % 2 == 0) {
                    return "Even";
                } else {
                    return "Odd";
                }
            });

        StepVerifier.create(oddEvenFlux)
            .expectNext("Odd")
            .expectNext("Even")
            .expectNext("Odd")
            .expectNext("Even")
            .expectNext("Odd")
            .expectNext("Even")
            .verifyComplete();
    }

    @Test
    public void givenNumberMono_whenMappedToOddOrEven_thenReturnCorrectLabel() {
        Mono<String> oddMono = Mono.just(1)
            .map(num -> {
                if (num % 2 == 0) {
                    return "Even";
                } else {
                    return "Odd";
                }
            });

        StepVerifier.create(oddMono)
            .expectNext("Odd")
            .verifyComplete();

        Mono<String> evenMono = Mono.just(2)
            .map(num -> {
                if (num % 2 == 0) {
                    return "Even";
                } else {
                    return "Odd";
                }
            });

        StepVerifier.create(evenMono)
            .expectNext("Even")
            .verifyComplete();
    }

    @Test
    public void givenNumbersFlux_whenFilteredForEvenNumbers_thenReturnOnlyEvenNumbers() {
        Flux<Integer> evenNumbersFlux = Flux.just(1, 2, 3, 4, 5, 6)
            .filter(num -> num % 2 == 0);

        StepVerifier.create(evenNumbersFlux)
            .expectNext(2)
            .expectNext(4)
            .expectNext(6)
            .verifyComplete();
    }

    @Test
    public void givenMono_whenFilteredForEvenNumber_thenReturnOnlyEvenNumber() {
        Mono<Integer> oddMono = Mono.just(1)
            .filter(num -> num % 2 == 0);
        StepVerifier.create(oddMono)
            .verifyComplete();

        Mono<Integer> evenMono = Mono.just(2)
            .filter(num -> num % 2 == 0);
        StepVerifier.create(evenMono)
            .expectNext(2)
            .verifyComplete();
    }

    @Test
    public void givenFilteredFluxIsEmpty_whenSwitchIfEmptyApplied_thenReturnFallbackValues() {
        Flux<String> flux = Flux.just("A", "B", "C", "D", "E")
            .filter(word -> word.length() >= 2);
        StepVerifier.create(flux)
            .verifyComplete();

        flux = flux.switchIfEmpty(Flux.defer(() -> Flux.just("AA", "BB", "CC")));
        StepVerifier.create(flux)
            .expectNext("AA")
            .expectNext("BB")
            .expectNext("CC")
            .verifyComplete();
    }

    @Test
    public void givenFilteredMonoIsEmpty_whenSwitchIfEmptyApplied_thenReturnFallbackValue() {
        Mono<String> mono = Mono.just("A")
            .filter(word -> word.length() >= 2);
        StepVerifier.create(mono)
            .verifyComplete();

        mono = mono.switchIfEmpty(Mono.defer(() -> Mono.just("AA")));
        StepVerifier.create(mono)
            .expectNext("AA")
            .verifyComplete();
    }

    @Test
    public void givenFilteredFluxIsEmpty_whenDefaultIfEmptyApplied_thenReturnDefaultValue() {
        Flux<String> flux = Flux.just("A", "B", "C", "D", "E")
            .filter(word -> word.length() >= 2);
        StepVerifier.create(flux)
            .verifyComplete();

        flux = flux.defaultIfEmpty("No words found!");
        StepVerifier.create(flux)
            .expectNext("No words found!")
            .verifyComplete();
    }

    @Test
    public void givenFilteredMonoIsEmpty_whenDefaultIfEmptyApplied_thenReturnDefaultValue() {
        Mono<String> mono = Mono.just("A")
            .filter(word -> word.length() >= 2);
        StepVerifier.create(mono)
            .verifyComplete();

        mono = mono.defaultIfEmpty("No word found!");
        StepVerifier.create(mono)
            .expectNext("No word found!")
            .verifyComplete();
    }

    @Test
    public void givenStringFlux_whenFlatMapped_thenReturnTransformedFlattenedSequence() {
        Flux<String> flux = Flux.just("A", "B", "C")
            .flatMap(word -> {
                if (word.startsWith("A")) {
                    return Flux.just(word + "1", word + "2", word + "3");
                } else {
                    return Flux.just(word);
                }
            })
            .flatMap(word -> {
                if (word.startsWith("B")) {
                    return Flux.just(word + "1", word + "2");
                } else {
                    return Flux.just(word);
                }
            });

        StepVerifier.create(flux)
            .expectNext("A1")
            .expectNext("A2")
            .expectNext("A3")
            .expectNext("B1")
            .expectNext("B2")
            .expectNext("C")
            .verifyComplete();
    }

    @Test
    public void givenStringMono_whenFlatMapped_thenReturnTransformedFlattenedSequence() {
        Flux<String> flux = Mono.just("A")
            .flatMapMany(word -> {
                if (word.startsWith("A")) {
                    return Flux.just(word + "1", word + "2", word + "3", "B", "C");
                } else {
                    return Flux.just(word);
                }
            })
            .flatMap(word -> {
                if (word.startsWith("B")) {
                    return Flux.just(word + "1", word + "2");
                } else {
                    return Flux.just(word);
                }
            });

        StepVerifier.create(flux)
            .expectNext("A1")
            .expectNext("A2")
            .expectNext("A3")
            .expectNext("B1")
            .expectNext("B2")
            .expectNext("C")
            .verifyComplete();
    }

    @Test
    public void givenNumbersFlux_whenEvenNumberEncountered_thenIncrementCounter() {
        AtomicInteger evenCounter = new AtomicInteger(0);

        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6)
            .doOnNext(num -> {
                if (num % 2 == 0) {
                    evenCounter.incrementAndGet();
                }
            });

        StepVerifier.create(flux)
            .expectNextMatches(num -> num == 1 && evenCounter.get() == 0)
            .expectNextMatches(num -> num == 2 && evenCounter.get() == 1)
            .expectNextMatches(num -> num == 3 && evenCounter.get() == 1)
            .expectNextMatches(num -> num == 4 && evenCounter.get() == 2)
            .expectNextMatches(num -> num == 5 && evenCounter.get() == 2)
            .expectNextMatches(num -> num == 6 && evenCounter.get() == 3)
            .verifyComplete();
    }

    @Test
    public void givenMono_whenEvenNumberEncountered_thenIncrementCounter() {
        AtomicInteger evenCounter = new AtomicInteger(0);
        Mono<Integer> oddMono = Mono.just(1)
            .doOnNext(num -> {
                if (num % 2 == 0) {
                    evenCounter.incrementAndGet();
                }
            });
        StepVerifier.create(oddMono)
            .expectNextMatches(num -> num == 1 && evenCounter.get() == 0)
            .verifyComplete();

        evenCounter.set(0);
        Mono<Integer> evenMono = Mono.just(2)
            .doOnNext(num -> {
                if (num % 2 == 0) {
                    evenCounter.incrementAndGet();
                }
            });
        StepVerifier.create(evenMono)
            .expectNextMatches(num -> num == 2 && evenCounter.get() == 1)
            .verifyComplete();
    }

    @Test
    public void givenNumbersFlux_whenFluxCompletes_thenSetDoneFlagToTrue() {
        AtomicBoolean done = new AtomicBoolean(false);

        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6)
            .doOnComplete(() -> done.set(true));

        StepVerifier.create(flux)
            .expectNextMatches(num -> num == 1 && !done.get())
            .expectNextMatches(num -> num == 2 && !done.get())
            .expectNextMatches(num -> num == 3 && !done.get())
            .expectNextMatches(num -> num == 4 && !done.get())
            .expectNextMatches(num -> num == 5 && !done.get())
            .expectNextMatches(num -> num == 6 && !done.get())
            .then(() -> Assertions.assertTrue(done.get()))
            .expectComplete()
            .verify();
    }

    @Test
    public void givenMono_whenMonoEmits_thenSetDoneFlagToTrue() {
        AtomicBoolean done = new AtomicBoolean(false);

        Mono<Integer> mono = Mono.just(1)
            .doOnSuccess(num -> done.set(true));

        StepVerifier.create(mono)
            .expectNextMatches(num -> num == 1 && done.get())
            .verifyComplete();
    }

    @Test
    public void givenTwoMonos_whenZippedTogether_thenReturnCombinedResult() {
        int userId = 1;
        Mono<String> userAgeCategory = Mono.defer(() -> Mono.just(userId))
            .zipWhen(id -> Mono.just(20), (id, age) -> age >= 18 ? "ADULT" : "KID");
        StepVerifier.create(userAgeCategory)
            .expectNext("ADULT")
            .verifyComplete();

        Mono<String> noUserAgeCategory = Mono.empty()
            .zipWhen(id -> Mono.defer(() -> Mono.just(20)), (id, age) -> age >= 18 ? "ADULT" : "KID");
        StepVerifier.create(noUserAgeCategory)
            .verifyComplete();
    }

    @Test
    public void givenTwoDeferredMonos_whenFirstWithValueCalled_thenReturnFirstAvailableMono() {
        Mono<String[]> source1 = Mono.defer(() -> Mono.just(new String[] { "val", "source1" })
            .delayElement(Duration.ofMillis(200)));
        Mono<String[]> source2 = Mono.defer(() -> Mono.just(new String[] { "val", "source2" })
            .delayElement(Duration.ofMillis(10)));
        Mono<String[]> mono = Mono.firstWithValue(source1, source2);

        StepVerifier.create(mono)
            .expectNextMatches(item -> "val".equals(item[0]) && "source2".equals(item[1]))
            .verifyComplete();
    }

    @Test
    public void givenTwoDeferredMonos_whenZippedAndCompared_thenReturnTrueIfEqual() {
        Mono<String> dataFromDB = Mono.defer(() -> Mono.just("db_val")
            .delayElement(Duration.ofMillis(200)));
        Mono<String> dataFromCache = Mono.defer(() -> Mono.just("cache_val")
            .delayElement(Duration.ofMillis(10)));
        Mono<String[]> mono = Mono.zip(dataFromDB, dataFromCache, (dbValue, cacheValue) -> new String[] { dbValue, dbValue.equals(cacheValue) ? "VALID_CACHE" : "INVALID_CACHE" });

        StepVerifier.create(mono)
            .expectNextMatches(item -> "db_val".equals(item[0]) && "INVALID_CACHE".equals(item[1]))
            .verifyComplete();
    }

    @Test
    public void givenTwoDeferredFlux_whenZippedAndCompared_thenReturnTrueIfEqual() {
        Flux<String> dataFromDB = Flux.defer(() -> Flux.just("db_val")
            .delayElements(Duration.ofMillis(200)));
        Flux<String> dataFromCache = Flux.defer(() -> Flux.just("cache_val")
            .delayElements(Duration.ofMillis(10)));
        Flux<String[]> flux = Flux.zip(dataFromDB, dataFromCache, (dbValue, cacheValue) -> new String[] { dbValue, dbValue.equals(cacheValue) ? "VALID_CACHE" : "INVALID_CACHE" });

        StepVerifier.create(flux)
            .expectNextMatches(item -> "db_val".equals(item[0]) && "INVALID_CACHE".equals(item[1]))
            .verifyComplete();
    }

    @Test
    public void givenMonoThrowsError_whenHandledWithOnErrorResume_thenReturnFallbackValue() {
        Mono<Object> mono = Mono.defer(() -> Mono.error(new RuntimeException("Not found")))
            .onErrorResume(ex -> {
                if (ex instanceof RuntimeException && "Not found".equals(ex.getMessage())) {
                    return Mono.just(0);
                } else {
                    return Mono.error(ex);
                }
            });

        StepVerifier.create(mono)
            .expectNext(0)
            .verifyComplete();
    }

    @Test
    public void givenFluxEncountersError_whenOnErrorResumeApplied_thenFallbackAfterRetries() {
        AtomicInteger counter = new AtomicInteger(1);
        Flux<Integer> flux = Flux.defer(() -> {
                if (counter.get() > 2) {
                    return Flux.error(new RuntimeException("Not supported even number"));
                } else {
                    return Flux.just(counter.getAndIncrement());
                }
            })
            .repeat(5)
            .onErrorResume(ex -> Flux.defer(() -> Flux.just(100, 200)));

        StepVerifier.create(flux)
            .expectNext(1)
            .expectNext(2)
            .expectNext(100)
            .expectNext(200)
            .verifyComplete();
    }
}
