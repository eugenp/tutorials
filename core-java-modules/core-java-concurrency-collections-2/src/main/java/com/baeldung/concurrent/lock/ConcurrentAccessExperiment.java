package com.baeldung.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Supplier;

public abstract class ConcurrentAccessExperiment {

    public ConcurrentAccessExperiment() {
    }

    private Map<String,String> doMapSetup(String typeOfMap) {
        switch (typeOfMap) {
            case "HashMap":
                return new HashMap<String,String>();
            case "ConcurrentHashMap":
                return new ConcurrentHashMap<String,String>();
        }
        return null;
    }

    public final void doWork(String typeOfMap, int threads, int slots) {
        CompletableFuture<?>[] requests = new CompletableFuture<?>[threads * slots];
        Map<String,String> map = doMapSetup(typeOfMap);

        for (int i = 0; i < threads; i++) {
            requests[slots * i + 0] = CompletableFuture.supplyAsync(putSupplier(map, i));
            requests[slots * i + 1] = CompletableFuture.supplyAsync(getSupplier(map, i));
            requests[slots * i + 2] = CompletableFuture.supplyAsync(getSupplier(map, i));
            requests[slots * i + 3] = CompletableFuture.supplyAsync(getSupplier(map, i));
        }
        CompletableFuture.allOf(requests).join();
    }

    protected abstract Supplier<?> putSupplier(Map<String,String> map, int key);
    protected abstract Supplier<?> getSupplier(Map<String,String> map, int key);
}