package com.baeldung.vavr.future;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.exists;
import static io.vavr.Predicates.forAll;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CancellationException;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.verification.Timeout;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;

public class FutureUnitTest {

    private final String SUCCESS = "Success";
    private final String FAILURE = "Failure";

    @Test
    public void givenFunctionReturnInteger_WhenCallWithFuture_ShouldReturnFunctionValue() {
        Future<Integer> future = Future.of(() -> 1);

        assertEquals(1, future.get().intValue());
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
        Future<Integer> future = Future.of(() -> 1);
        MockConsumer consumer = Mockito.mock(MockConsumer.class);
        future.onSuccess(consumer);
        Mockito.verify(consumer, new Timeout(1000, VerificationModeFactory.times(1))).accept(1);
    }

    @Test
    public void givenFunctionThrowException_WhenCallWithFutureAndRegisterConsumerForFailer_ShouldCallConsumerToStoreException() {
        Future<String> future = Future.of(() -> getResourceThrowException(""));
        MockThrowableConsumer consumer = Mockito.mock(MockThrowableConsumer.class);
        future.onFailure(consumer);
        Mockito.verify(consumer, new Timeout(1000, VerificationModeFactory.times(1))).accept(Mockito.any());
    }

    @Test
    public void givenAFuture_WhenAddAndThenConsumer_ShouldCallConsumerWithResultOfFutureAction() {
        MockTryConsumer consumer1 = Mockito.mock(MockTryConsumer.class);
        MockTryConsumer consumer2 = Mockito.mock(MockTryConsumer.class);
        Future<Integer> future = Future.of(() -> 1);
        Future<Integer> andThenFuture = future.andThen(consumer1).andThen(consumer2);
        andThenFuture.await();
        Mockito.verify(consumer1, VerificationModeFactory.times(1)).accept(Try.success(1));
        Mockito.verify(consumer2, VerificationModeFactory.times(1)).accept(Try.success(1));
    }

    @Test
    public void givenAFailureFuture_WhenCallOrElseFunction_ShouldReturnNewFuture() {
        Future<Integer> future = Future.failed(new RuntimeException());
        Future<Integer> future2 = future.orElse(Future.of(() -> 2));

        assertEquals(2, future2.get().intValue());
    }

    @Test(expected = CancellationException.class)
    public void givenAFuture_WhenCallCancel_ShouldReturnCancellationException() {
        long waitTime = 1000;
        Future<Integer> future = Future.of(() -> {
            Thread.sleep(waitTime);
            return 1;
        });
        future.cancel();
        future.await();
        future.get();
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

        assertEquals(expectedResult, futureResult.get());
    }
    
    @Test
    public void givenAFuture_WhenTransformByAddingOne_ShouldReturn() {
        Future<Object> future = Future.of(() -> 1).transformValue(f -> Try.of(() -> "Hello: " + f.get()));
        
        assertEquals("Hello: 1", future.get());
    }
    
    @Test
     public void givenAFutureOfInt_WhenMapToString_ShouldCombineAndReturn() {
        Future<String> future = Future.of(()->1).map(i -> "Hello: " + i);
        
        assertEquals("Hello: 1", future.get());
    }
    
    @Test
    public void givenAFutureOfInt_WhenFlatMapToString_ShouldCombineAndReturn() {
        Future<Object> futureMap = Future.of(() -> 1).flatMap((i) -> Future.of(() -> "Hello: " + i));
        
        assertEquals("Hello: 1", futureMap.get());
    }
    
    @Test
    public void givenAFutureOf2String_WhenZip_ShouldReturnTupleOf2String() {
        Future<Tuple2<String, String>> future = Future.of(() -> "hello").zip(Future.of(() -> "world"));
        
        assertEquals(Tuple.of("hello", "world"), future.get());
    }

    @Test
    public void givenGetResourceWithFuture_WhenWaitAndMatchWithPredicate_ShouldReturnSuccess() {
        String url = "http://resource";
        Future<String> future = Future.of(() -> getResource(url));
        future.await();
        String s = Match(future).of(
          Case($(future0 -> future0.isSuccess()), SUCCESS),
          Case($(), FAILURE));

        assertEquals(SUCCESS, s);
    }

    @Test
    public void givenAFailedFuture_WhenWaitAndMatchWithPredicateCheckSuccess_ShouldReturnFailed() {
        Future<Integer> future = Future.failed(new RuntimeException());
        future.await();
        String s = Match(future).of(
          Case($(future0 -> future0.isSuccess()), SUCCESS),
          Case($(), FAILURE));

        assertEquals(FAILURE, s);
    }

    @Test
    public void givenAFuture_WhenMatchWithFuturePredicate_ShouldReturnSuccess() {
        Future<Integer> future = Future.of(() -> {
            Thread.sleep(10);
            return 1;
        });
        Predicate<Future<Integer>> predicate = f -> f.exists(i -> i % 2 == 1);

        String s = Match(future).of(
          Case($(predicate), "Even"),
          Case($(), "Odd"));

        assertEquals("Even", s);
    }

    @Test
    public void givenAListOfFutureReturnFist3Integers_WhenMatchWithExistEvenNumberPredicate_ShouldReturnSuccess() {
        List<Future<Integer>> futures = getFutureOfFirst3Number();
        Predicate<Future<Integer>> predicate0 = future -> future.exists(i -> i % 2 == 0);
        String s = Match(futures).of(
          Case($(exists(predicate0)), "Even"),
          Case($(), "Odd"));

        assertEquals("Even", s);
    }

    @Test
    public void givenAListOfFutureReturnFist3Integers_WhenMatchWithForAllNumberBiggerThanZeroPredicate_ShouldReturnSuccess() {
        List<Future<Integer>> futures = getFutureOfFirst3Number();
        Predicate<Future<Integer>> predicate0 = future -> future.exists(i -> i > 0);
        String s = Match(futures).of(
          Case($(forAll(predicate0)), "Positive numbers"),
          Case($(), "None"));

        assertEquals("Positive numbers", s);
    }

    @Test
    public void givenAListOfFutureReturnFist3Integers_WhenMatchWithForAllNumberSmallerThanZeroPredicate_ShouldReturnFailed() {
        List<Future<Integer>> futures = getFutureOfFirst3Number();
        Predicate<Future<Integer>> predicate0 = future -> future.exists(i -> i < 0);
        String s = Match(futures).of(
          Case($(forAll(predicate0)), "Negative numbers"),
          Case($(), "None"));

        assertEquals("None", s);
    }

    private String getResource(String url) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Content from " + url;
    }

    private String getResourceThrowException(String url) {
        throw new RuntimeException("Exception when get resource " + url);
    }

    private List<Future<Integer>> getFutureOfFirst3Number() {
        List<Future<Integer>> futures = List.of(Future.of(() -> 1), Future.of(() -> 2), Future.of(() -> 3));
        return futures;
    }
    
    private static void checkOnSuccessFunction() {
        Future<Integer> future = Future.of(() -> 1);
        future.onSuccess(i -> System.out.println("Future finish with result: " + i));
    }
    
    private static void checkOnFailureFunction() {
        Future<Integer> future = Future.of(() -> {throw new RuntimeException("Failed");});
        future.onFailure(t -> System.out.println("Future failures with exception: " + t));
    }
    
    private static void runAndThenConsumer() {
        Future<Integer> future = Future.of(() -> 1);
        future.andThen(i -> System.out.println("Do side-effect action 1 with input: " + i.get())).
          andThen((i) -> System.out.println("Do side-effect action 2 with input: " + i.get()));
    }
    
    public static void main(String[] args) throws InterruptedException {
        checkOnSuccessFunction();
        checkOnFailureFunction();
        runAndThenConsumer();
        Thread.sleep(1000);
    }
}


class MockConsumer implements Consumer<Integer> {
    @Override
    public void accept(Integer t) {
    }
}

class MockTryConsumer implements Consumer<Try<Integer>> {
    @Override
    public void accept(Try<Integer> t) {
    }
}

class MockThrowableConsumer implements Consumer<Throwable> {
    @Override
    public void accept(Throwable t) {
    }
}
