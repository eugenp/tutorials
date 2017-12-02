package com.vavr.future;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;

import org.junit.Test;

import io.vavr.concurrent.Future;

public class Tester {

    @Test
    public void whenAppendData_thenCorrect() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> appendData(initialValue));
        String result = resultFuture.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    @Test
    public void whenAppendData_thenSuccess() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> appendData(initialValue))
            .onSuccess(finalResult -> System.out.println("Successfully Completed - Result: " + finalResult))
            .onFailure(finalResult -> System.out.println("Failed - Result: " + finalResult));
        String result = resultFuture.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    @Test
    public void whenChainingCallbacks_thenCorrect() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> appendData(initialValue))
            .andThen(finalResult -> System.out.println("Completed - 1: " + finalResult))
            .andThen(finalResult -> System.out.println("Completed - 2: " + finalResult));
        String result = resultFuture.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    @Test
    public void whenCallAwait_thenCorrect() {
        String initialValue = "Welcome to ";
        Future<String> resultFuture = Future.of(() -> appendData(initialValue));
        resultFuture = resultFuture.await();
        String result = resultFuture.get();

        assertEquals("Welcome to Baeldung!", result);
    }

    public String appendData(String initial) {
        return initial + "Baeldung!";
    }
}
