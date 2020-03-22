package com.baeldung.guava.concurrency;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class ListenableFutureExampleUnitTest {

    private ListeningExecutorService service;
    private static Logger logger = Logger.getAnonymousLogger();

    @Before
    public void setUp() {
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
    }

    @After
    public void tearDown() {
        service.shutdown();
    }

    // working with listeners
    @Test
    public void givenFuture_whenAddListenerCalled_thenCanGetResult() {
        ListenableFuture<String> future = service.submit(() -> "Welcome to ");
        future.addListener(() -> {
            try {
                logger.info(future.get() + "Baeldung!");
            } catch (InterruptedException | ExecutionException e) {
                logger.info("Error getting result from Future");
            }
        }, service);
    }

    // working with callbacks - onSuccess
    @Test
    public void givenSuccessfulFuture_whenAddCallback_thenCanGetResult() {
        ListenableFuture<String> future = service.submit(() -> "Welcome to ");
        FutureCallback<String> callback = getFutureCallback(future);

        Futures.addCallback(future, callback, service);
    }

    // working with callbacks - onFailure
    @Test
    public void givenFailedFuture_whenAddCallback_thenThrowsException() {
        Callable npeCallable = () -> {
          throw new NullPointerException();
        };
        ListenableFuture<String> failingFuture = service.submit(npeCallable);
        FutureCallback<String> callback = getFutureCallback(failingFuture);
        Futures.addCallback(failingFuture, callback, service);
    }

    // working with a list of Futures
    @Test
    public void givenFuturesRanAsList_thenCanGetResult() throws ExecutionException, InterruptedException {
        ListenableFuture<List<Integer>> all = Futures.allAsList(
          service.submit(() -> 100),
          service.submit(() -> 300));
        List<Integer> results = all.get();
        Integer sum = results.stream().reduce(0, Integer::sum);

        assertEquals(400, sum.intValue());
    }

    // Transforming Futures
    @Test
    public void givenFuture_whenTransformed_thenCanGetResult() throws ExecutionException, InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
        ListenableFuture<Integer> future = service.submit(() -> 10);
        AsyncFunction<Integer, Integer> asyncFn = num -> service.submit(() -> num * num);
        ListenableFuture<Integer> result = Futures.transformAsync(future, asyncFn, service);

        assertEquals(100, result.get().intValue());
    }

    private FutureCallback<String> getFutureCallback(ListenableFuture<String> future) {
        return new FutureCallback<String>() {
          @Override
          public void onSuccess(@Nullable String result) {
              try {
                  logger.info(future.get() + "Baeldung!");
              } catch (InterruptedException | ExecutionException e) {
                  logger.info("Error getting result from Future");
              }
          }

          @Override
          public void onFailure(Throwable t) {
            logger.info("onFailure invoked");
          }
        };
    }

}
