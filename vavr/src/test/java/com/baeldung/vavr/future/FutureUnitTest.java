package com.baeldung.vavr.future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.vavr.concurrent.Future;

public class FutureUnitTest {

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
        future.onFailure(err -> {
            store[0] = err;
        });
        while (store[0] == null) {
        }
        assertEquals(store[0].getClass(), RuntimeException.class);
    }

    private String getResource(String url) throws InterruptedException {
        Thread.sleep(10);
        return "Content from " + url;
    }

    private String getResourceThrowException(String url) {
        throw new RuntimeException("Exception when get resource " + url);
    }

}
