package main.java.com.baeldung.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Supplier;

public abstract class ConcurrentAccessMap {
    
    public ConcurrentAccessMap() {
    }
    
    private Map<String, String> getHashMap() {
        return new HashMap<String,String>();
    }

    private Map<String, String> getConcurrentHashMap() {
        return new ConcurrentHashMap<String,String>();
    }
    
    private Map<String,String> setup(String type) {
        switch (type) {
            case "HashMap":
                return getHashMap();
            case "ConcurrentHashMap":
                return getConcurrentHashMap();
        }
        return null;
    }

    public final void doWork(String type, int threads, int slots) {
         CompletableFuture<?>[] requests = new CompletableFuture<?>[threads * slots];
         Map<String,String> map = setup(type);

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