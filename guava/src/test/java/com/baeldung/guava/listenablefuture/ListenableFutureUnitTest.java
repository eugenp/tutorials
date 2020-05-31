package com.baeldung.guava.listenablefuture;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class ListenableFutureUnitTest {

    private static ListeningExecutorService executor;

    @BeforeClass
    public static void beforeClass() {
        executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2));
    }

    @AfterClass
    public static void afterClass() {
        executor.shutdown();
    }

    @Test
    public void whenCalled_shouldCompute() throws ExecutionException, InterruptedException {
        ListenableFuture<Integer> future = executor.submit(new LongRunningOperation(42));

        Integer result = future.get();
        assertThat(result).isEqualTo(42);
    }

    @Test
    public void whenTransform_shouldCompute() throws ExecutionException, InterruptedException {
        ListenableFuture<Integer> future = executor.submit(new LongRunningOperation(42));
        ListenableFuture<Integer> timesTwoFuture = Futures.transform(future, e -> e * 2, executor);

        assertThat(timesTwoFuture.get()).isEqualTo(84);
    }

    @Test
    public void whenTransformAsync_shouldCompute() throws ExecutionException, InterruptedException {
        ListenableFuture<Integer> future = executor.submit(new LongRunningOperation(42));
        ListenableFuture<Integer> timesTwoFuture = Futures.transformAsync(
                future,
                e -> executor.submit(new LongRunningOperation(e * 2)),
                executor);

        assertThat(timesTwoFuture.get()).isEqualTo(84);
    }

    @Test
    public void whenAllAsList_shouldReturnFutureList() throws ExecutionException, InterruptedException {
        ListenableFuture<Integer> future1 = executor.submit(new LongRunningOperation(42));
        ListenableFuture<Integer> future2 = executor.submit(new LongRunningOperation(24));
        ListenableFuture<List<Integer>> futureList = Futures.allAsList(future1, future2);

        ListenableFuture<Integer> result = Futures.transform(futureList, this::sum, executor);
        assertThat(result.get()).isEqualTo(66);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Integer sum(List<Integer> list) {
        return list.stream().reduce(Integer::sum).get();
    }
}
