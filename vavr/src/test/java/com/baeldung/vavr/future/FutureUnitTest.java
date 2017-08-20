package com.baeldung.vavr.future;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.exists;
import static io.vavr.Predicates.forAll;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CancellationException;
import java.util.function.Predicate;

import org.junit.Test;

import io.vavr.collection.List;
import io.vavr.concurrent.Future;

public class FutureUnitTest {

    private final String SUCCESS = "Success";
    private final String FAILURE = "Failure";

    @Test
    public void givenFunctionReturnInteger_WhenCallWithFuture_ShouldReturnFunctionValue() {
        Future<Integer> future = Future.of(() -> 1);

        assertEquals(1, future.get()
            .intValue());
    }

    @Test
    public void givenFunctionGetRemoteHttpResourceAsString_WhenCallSuccessWithFuture_ShouldReturnContentValueAsString() {
        String url = "http://resource";
        String content = "Content from " + url;
        Future<String> future = Future.of(() -> getResource(url));

        assertEquals(content, future.get());
    }

    @Test
    public void givenFunctionThrowException_WhenCallWithFuture_ShouldReturnFailure() {
        Future<String> future = Future.of(() -> getResourceThrowException(""));
        future.await();

        assertTrue(future.isFailure());
    }

    @Test
    public void givenAFutureReturnZero_WhenCheckFutureWithExistEvenValue_ShouldReturnRight() {
        Future<Integer> future = Future.of(() -> 2);
        boolean result = future.exists(i -> i % 2 == 0);

        assertTrue(result);
    }

    @Test
    public void givenFunction_WhenCallWithFutureAndRegisterConsumerForSuccess_ShouldCallConsumerToStoreValue() {
        final int[] store = new int[] { 0 };
        Future<Integer> future = Future.of(() -> 1);
        future.onSuccess(i -> {
            store[0] = i;
        });
        while (store[0] == 0) {
        }

        assertEquals(1, store[0]);
    }

    @Test
    public void givenFunctionThrowException_WhenCallWithFutureAndRegisterConsumerForFailer_ShouldCallConsumerToStoreException() {
        final Throwable[] store = new Throwable[] { null };
        Future<String> future = Future.of(() -> getResourceThrowException(""));
        future.onFailure(err -> store[0] = err);
        while (store[0] == null) {
        }

        assertEquals(store[0].getClass(), RuntimeException.class);
    }

    @Test
    public void givenAFuture_WhenAddAndThenConsumer_ShouldCallConsumerWithResultOfFutureAction() {
        int[] store1 = new int[1];
        int[] store2 = new int[1];
        Future<Integer> future = Future.of(() -> 1);
        future.andThen(i -> store1[0] = i.get() + 1)
            .andThen(i -> store2[0] = store1[0] + 1);
        while (store2[0] == 0) {
        }

        assertEquals(2, store1[0]);
        assertEquals(3, store2[0]);
    }

    @Test
    public void givenAFailureFuture_WhenCallOrElseFunction_ShouldReturnNewFuture() {
        Future<Integer> future = Future.failed(new RuntimeException());
        Future<Integer> future2 = future.orElse(Future.of(() -> 2));

        assertEquals(2, future2.get()
            .intValue());
    }

    @Test
    public void givenAFuture_WhenCallCancel_ShouldReturnCancellationException() {
        long waitTime = 1000;
        Future<Integer> future = Future.of(() -> {
            Thread.sleep(waitTime);
            return 1;
        });
        future.cancel();
        future.await();
        assertEquals(CancellationException.class, future.getCause().get().getClass());
    }

    @Test
    public void givenAFuture_WhenCallFallBackWithSuccessFuture_ShouldReturnFutureResult() {
        String expectedResult = "take this";
        Future<String> future = Future.of(() -> expectedResult);
        Future<String> secondFuture = Future.of(() -> "take that");
        Future<String> futureResult = future.fallbackTo(secondFuture);
        futureResult.await();

        assertEquals(expectedResult, futureResult.get());
    }

    @Test
    public void givenAFuture_WhenCallFallBackWithFailureFuture_ShouldReturnValueOfFallBackFuture() {
        String expectedResult = "take that";
        Future<String> future = Future.failed(new RuntimeException());
        Future<String> fallbackFuture = Future.of(() -> expectedResult);
        Future<String> futureResult = future.fallbackTo(fallbackFuture);
        futureResult.await();

        assertEquals(expectedResult, futureResult.get());
    }

    @Test
    public void givenGetResourceWithFuture_WhenWaitAndMatchWithPredicate_ShouldReturnSuccess() {
        String url = "http://resource";
        Future<String> future = Future.of(() -> getResource(url));
        future.await();
        String s = Match(future).of(Case($(future0 -> future0.isSuccess()), SUCCESS), Case($(), FAILURE));

        assertEquals(SUCCESS, s);
    }

    @Test
    public void givenAFailedFuture_WhenWaitAndMatchWithPredicateCheckSuccess_ShouldReturnFailed() {
        Future<Integer> future = Future.failed(new RuntimeException());
        future.await();
        String s = Match(future).of(Case($(future0 -> future0.isSuccess()), SUCCESS), Case($(), FAILURE));

        assertEquals(FAILURE, s);
    }

    @Test
    public void givenAFuture_WhenMatchWithFuturePredicate_ShouldReturnSuccess() {
        Future<Integer> future = Future.of(() -> {
            Thread.sleep(10);
            return 1;
        });
        Predicate<Future<Integer>> predicate = f -> f.exists(i -> i % 2 == 1);
        String s = Match(future).of(Case($(predicate), "Even"), Case($(), "Odd"));

        assertEquals("Even", s);
    }

    @Test
    public void givenAListOfFutureReturnFist3Integers_WhenMatchWithExistEvenNumberPredicate_ShouldReturnSuccess() {
        List<Future<Integer>> futures = getFutureOfFirst3Number();
        Predicate<Future<Integer>> predicate0 = future -> future.exists(i -> i % 2 == 0);
        String s = Match(futures).of(Case($(exists(predicate0)), "Even"), Case($(), "Odd"));

        assertEquals("Even", s);
    }

    @Test
    public void givenAListOfFutureReturnFist3Integers_WhenMatchWithForAllNumberBiggerThanZeroPredicate_ShouldReturnSuccess() {
        List<Future<Integer>> futures = getFutureOfFirst3Number();
        Predicate<Future<Integer>> predicate0 = future -> future.exists(i -> i > 0);
        String s = Match(futures).of(Case($(forAll(predicate0)), "Positive numbers"), Case($(), "None"));

        assertEquals("Positive numbers", s);
    }

    @Test
    public void givenAListOfFutureReturnFist3Integers_WhenMatchWithForAllNumberSmallerThanZeroPredicate_ShouldReturnFailed() {
        List<Future<Integer>> futures = getFutureOfFirst3Number();
        Predicate<Future<Integer>> predicate0 = future -> future.exists(i -> i < 0);
        String s = Match(futures).of(Case($(forAll(predicate0)), "Negative numbers"), Case($(), "None"));

        assertEquals("None", s);
    }

    private String getResource(String url) throws InterruptedException {
        Thread.sleep(10);
        return "Content from " + url;
    }

    private String getResourceThrowException(String url) {
        throw new RuntimeException("Exception when get resource " + url);
    }

    private List<Future<Integer>> getFutureOfFirst3Number() {
        List<Future<Integer>> futures = List.of(Future.of(() -> 1), Future.of(() -> 2), Future.of(() -> 3));
        return futures;
    }
}
