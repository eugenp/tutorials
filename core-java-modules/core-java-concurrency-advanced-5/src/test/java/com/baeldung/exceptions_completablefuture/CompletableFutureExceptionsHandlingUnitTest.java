package com.baeldung.exceptions_completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CompletableFutureExceptionsHandlingUnitTest {

    @ParameterizedTest
    @MethodSource("parametersSource_handle")
    void whenCompletableFutureIsScheduled_thenHandleStageIsAlwaysInvoked(int radius, long expected)
      throws ExecutionException, InterruptedException {
        long actual = CompletableFuture
          .supplyAsync(() -> {
              if (radius <= 0) {
                  throw new IllegalArgumentException("Supplied with non-positive radius '%d'");
              }
              return Math.round(Math.pow(radius, 2) * Math.PI);
          })
          .handle((result, ex) -> {
              if (ex == null) {
                  return result;
              } else {
                  return -1L;
              }
          })
          .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("parametersSource_exceptionally")
    void whenCompletableFutureIsScheduled_thenExceptionallyExecutedOnlyOnFailure(int a, int b, int c, long expected)
      throws ExecutionException, InterruptedException {
        long actual = CompletableFuture
          .supplyAsync(() -> {
              if (a <= 0 || b <= 0 || c <= 0) {
                  throw new IllegalArgumentException(String.format("Supplied with incorrect edge length [%s]", List.of(a, b, c)));
              }
              return a * b * c;
          })
          .exceptionally((ex) -> -1)
          .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("parametersSource_exceptionally")
    void givenCompletableFutureIsScheduled_whenHandleIsAlreadyPresent_thenExceptionallyIsNotExecuted(int a, int b, int c, long expected)
      throws ExecutionException, InterruptedException {
        long actual = CompletableFuture
          .supplyAsync(() -> {
              if (a <= 0 || b <= 0 || c <= 0) {
                  throw new IllegalArgumentException(String.format("Supplied with incorrect edge length [%s]", List.of(a, b, c)));
              }
              return a * b * c;
          })
          .handle((result, throwable) -> {
              if (throwable != null) {
                  return -1;
              }
              return result;
          })
          .exceptionally((ex) -> {
              System.exit(1);
              return 0;
          })
          .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("parametersSource_whenComplete")
    void whenCompletableFutureIsScheduled_thenWhenCompletedExecutedAlways(Double a, long expected, Class<Exception> ifAny) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);

            long actual = CompletableFuture
              .supplyAsync(() -> {
                  if (a.isNaN()) {
                      throw new IllegalArgumentException("Supplied value is NaN");
                  }
                  return Math.round(Math.pow(a, 2));
              })
              .whenComplete((result, exception) -> countDownLatch.countDown())
              .get();

            Assertions.assertThat(countDownLatch.await(20L, java.util.concurrent.TimeUnit.SECONDS));
            Assertions.assertThat(actual).isEqualTo(expected);
        } catch (Exception e) {
            Assertions.assertThat(e.getClass()).isSameAs(ExecutionException.class);
            Assertions.assertThat(e.getCause().getClass()).isSameAs(ifAny);
        }
    }

    static Stream<Arguments> parametersSource_handle() {
        return Stream.of(
          Arguments.of(1, 3),
          Arguments.of(-1, -1)
        );
    }

    static Stream<Arguments> parametersSource_exceptionally() {
        return Stream.of(
          Arguments.of(1, 5, 5, 25),
          Arguments.of(-1, 10, 15, -1)
        );
    }

    static Stream<Arguments> parametersSource_whenComplete() {
        return Stream.of(
          Arguments.of(2d, 4, null),
          Arguments.of(Double.NaN, 1, IllegalArgumentException.class)
        );
    }
}
