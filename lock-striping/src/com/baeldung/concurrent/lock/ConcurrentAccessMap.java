package com.baeldung.concurrent.lock;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.common.base.Supplier;

public abstract class ConcurrentAccessMap {
	static final int SLOTS = 4;
	static final int THREADS = 10000;
	static final int BUCKETS = Runtime.getRuntime().availableProcessors() * SLOTS;
	private CompletableFuture<?>[] requests;
	Map<String, String> map;
		
	public ConcurrentAccessMap(Map<String, String> map) {
		this.map = map;
	}
	
	public final void doWork(String type) {
		requests = new CompletableFuture<?>[THREADS * SLOTS];

		for (int i = 0; i < THREADS; i++) {
        	requests[SLOTS * i + 0] = CompletableFuture.supplyAsync(putSupplier(i));
    		requests[SLOTS * i + 1] = CompletableFuture.supplyAsync(getSupplier(i));
    		requests[SLOTS * i + 2] = CompletableFuture.supplyAsync(getSupplier(i));
    		requests[SLOTS * i + 3] = CompletableFuture.supplyAsync(getSupplier(i)); 	
		}
		CompletableFuture.allOf(requests).join();
	}
		
	protected abstract Supplier<?> putSupplier(int x);
	protected abstract Supplier<?> getSupplier(int x);
}
