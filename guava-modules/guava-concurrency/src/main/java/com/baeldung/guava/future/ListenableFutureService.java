package com.baeldung.guava.future;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baeldung.guava.future.exception.ListenableFutureException;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ListenableFutureService {

    private final ListeningExecutorService lExecService;

    public ListenableFutureService() {
        this.lExecService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
    }

    public ListenableFutureService(ListeningExecutorService lExecService) {
        this.lExecService = lExecService;
    }

    public ListenableFuture<String> fetchConfig(String configKey) {
        return lExecService.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return String.format("%s.%d", configKey, new Random().nextInt(Integer.MAX_VALUE));
        });
    }

    public FutureTask<String> fetchConfigTask(String configKey) {
        return new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return String.format("%s.%d", configKey, new Random().nextInt(Integer.MAX_VALUE));
        });
    }

    public ListenableFutureTask<String> fetchConfigListenableTask(String configKey) {
        return ListenableFutureTask.create(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return String.format("%s.%d", configKey, new Random().nextInt(Integer.MAX_VALUE));
        });
    }

    public ListenableFuture<Integer> succeedingTask() {
        return Futures.immediateFuture(new Random().nextInt(Integer.MAX_VALUE));
    }

    public <T> ListenableFuture<T> failingTask() {
        return Futures.immediateFailedFuture(new ListenableFutureException());
    }

    public ListenableFuture<Integer> getCartId() {
        return lExecService.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return new Random().nextInt(Integer.MAX_VALUE);
        });
    }

    public ListenableFuture<String> getCustomerName() {
        String[] names = new String[] { "Mark", "Jane", "June" };
        return lExecService.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return names[new Random().nextInt(names.length)];
        });
    }

    public ListenableFuture<List<String>> getCartItems() {
        String[] items = new String[] { "Apple", "Orange", "Mango", "Pineapple" };
        return lExecService.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);

            int noOfItems = new Random().nextInt(items.length);
            if (noOfItems == 0) ++noOfItems;

            return Arrays.stream(items, 0, noOfItems).collect(Collectors.toList());
        });
    }

    public ListenableFuture<String> generateUsername(String firstName) {
        return lExecService.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return firstName.replaceAll("[^a-zA-Z]+","")
                    .concat("@service.com");
        });
    }

    public ListenableFuture<String> generatePassword(String username) {
        return lExecService.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            if (username.contains("@")) {
                String[] parts = username.split("@");
                return parts[0] + "123@" + parts[1];
            } else {
                return username + "123";
            }
        });
    }
}