package com.baeldung.cancelflux;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.PrintStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

public class CancelFluxUnitTest {

    @Test
    void givenOngoingFlux_whentakeUntil_thenFluxCancels() {
        Flux<Integer> sensorData = Flux.range(1, 10);
        List<Integer> result = new ArrayList<>();

        sensorData.takeUntil(reading -> reading == 8)
          .subscribe(result::add);
        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Test
    void givenOngoingFlux_whentakeWhile_thenFluxCancels() {
        List<Integer> result = new ArrayList<>();
        Flux<Integer> sensorData = Flux.range(1, 10)
          .takeWhile(reading -> reading < 8)
          .doOnNext(result::add);

        sensorData.subscribe();
        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void givenOngoingFlux_whentake_thenFluxCancels() {
        Flux<Integer> sensorData = Flux.range(1, Integer.MAX_VALUE);
        List<Integer> result = new ArrayList<>();

        sensorData.take(2)
          .subscribe(result::add);
        assertThat(result).containsExactly(1, 2);
    }

    @Test
    void givenAnOnGoingFlux_whenTakeDurationElapsed_thenCancelsFlux() {
        Flux<Integer> sensorData = Flux.interval(Duration.ZERO, Duration.ofSeconds(2))
          .map(i -> i.intValue() + 10)
          .take(5);

        Flux<Integer> canceledByTimeout = sensorData.take(Duration.ofSeconds(3));

        StepVerifier.create(canceledByTimeout)
          .expectNext(10, 11)
          .expectComplete()
          .verify();
    }

    @Test
    void givenAnOnGoingFlux_whenDispose_thenCancelsFluxExplicitly() throws InterruptedException {
        Flux<Integer> flux = Flux.range(1, 10)
          .delayElements(Duration.ofSeconds(1));

        AtomicInteger count = new AtomicInteger(0);
        Disposable disposable = flux.subscribe(i -> {
            System.out.println("Received: " + i);
            count.incrementAndGet();
        }, e -> System.err.println("Error: " + e.getMessage()));

        Thread.sleep(4500);
        System.out.println("Will Dispose The flux Next");
        disposable.dispose();
        if (disposable.isDisposed()) {
            System.out.println("Flux Disposed");
        }
        assertEquals(4, count.get());
    }

    @Test
    void givenAFluxIsCanceled_whenDoOnCancelAndDoFinally_thenMessagePrinted() throws InterruptedException {

        List<Integer> result = new ArrayList<>();
        PrintStream mockPrintStream = mock(PrintStream.class);
        System.setOut(mockPrintStream);

        Flux<Integer> sensorData = Flux.interval(Duration.ofMillis(100))
          .doOnCancel(() -> System.out.println("Flux Canceled"))
          .doFinally(signalType -> {
              if (signalType == SignalType.CANCEL) {
                  System.out.println("Flux Completed due to Cancellation");
              } else {
                  System.out.println("Flux Completed due to Completion or Error");
              }
          })
          .map(i -> ThreadLocalRandom.current()
            .nextInt(1, 1001))
          .doOnNext(result::add);

        Disposable subscription = sensorData.subscribe();

        Thread.sleep(1000);
        subscription.dispose();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockPrintStream, times(2))
          .println(captor.capture());

        assertThat(captor.getAllValues()).contains("Flux Canceled", "Flux Completed due to Cancellation");
    }

}
