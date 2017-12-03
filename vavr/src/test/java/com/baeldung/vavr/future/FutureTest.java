package com.baeldung.vavr.future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import io.vavr.control.Try;

public class FutureTest {

    @Test
    public void whenAppendData_thenCorrect1() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        String result = resultFuture.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    @Test
    public void whenAppendData_thenCorrect2() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        resultFuture.await();
        Option<Try<String>> futureOption = resultFuture.getValue();
        Try<String> futureTry = futureOption.get();
        String result = futureTry.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    @Test
    public void whenAppendData_thenSuccess() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue))
            .onSuccess(finalResult -> System.out.println("Successfully Completed - Result: " + finalResult))
            .onFailure(finalResult -> System.out.println("Failed - Result: " + finalResult));
        String result = resultFuture.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    @Test
    public void whenChainingCallbacks_thenCorrect() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue))
            .andThen(finalResult -> System.out.println("Completed - 1: " + finalResult))
            .andThen(finalResult -> System.out.println("Completed - 2: " + finalResult));
        String result = resultFuture.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    @Test
    public void whenCallAwait_thenCorrect() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        resultFuture = resultFuture.await();
        String result = resultFuture.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    @Test
    public void whenDivideByZero_thenGetThrowable1() {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        Future<Throwable> throwableFuture = resultFuture.failed();
        Throwable throwable = throwableFuture.get();

        assertEquals("/ by zero", throwable.getMessage());
    }

    @Test
    public void whenDivideByZero_thenGetThrowable2() {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        resultFuture.await();
        Option<Throwable> throwableOption = resultFuture.getCause();
        Throwable throwable = throwableOption.get();

        assertEquals("/ by zero", throwable.getMessage());
    }

    @Test
    public void whenDivideByZero_thenCorrect() throws InterruptedException {
        Future<Integer> resultFuture = Future.of(() -> Util.divideByZero(10));
        resultFuture.await();

        assertTrue(resultFuture.isCompleted());
        assertFalse(resultFuture.isSuccess());
        assertTrue(resultFuture.isFailure());
    }

    @Test
    public void whenAppendData_thenFutureNotEmpty() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        resultFuture.await();

        assertFalse(resultFuture.isEmpty());
    }

    @Test
    public void whenCallZip_thenCorrect() {
        Future<Tuple2<String, Integer>> future = Future.of(() -> "John")
            .zip(Future.of(() -> new Integer(5)));
        future.await();

        assertEquals(Tuple.of("John", new Integer(5)), future.get());
    }

    @Test
    public void whenAppendData_thenFutureNotEmptyd() throws InterruptedException, ExecutionException {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> Util.appendData(initialValue));
        CompletableFuture<String> convertedFuture = resultFuture.toCompletableFuture();

        assertEquals("Welcome to Baeldung!", convertedFuture.get());
    }

    @Test
    public void whenCallMap_thenCorrect() {
        Future<String> futureResult = Future.of(() -> new StringBuilder("from Baeldung"))
            .map(a -> "Hello " + a);
        futureResult.await();

        assertEquals("Hello from Baeldung", futureResult.get());
    }
}
