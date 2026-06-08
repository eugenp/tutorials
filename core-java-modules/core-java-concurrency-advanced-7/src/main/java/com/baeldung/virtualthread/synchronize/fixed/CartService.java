package com.baeldung.virtualthread.synchronize.fixed;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final Map<String, Integer> products;
    private final Map<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public CartService() {
        this.products = new HashMap<>();
    }

    public void update(String productId, int quantity) {
        Lock lock = locks.computeIfAbsent(productId, k -> new ReentrantLock());

        try {
            if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                try {
                    simulateAPI();
                    products.merge(productId, quantity, Integer::sum);
                } finally {
                    lock.unlock();
                }
                LOGGER.info("Updated Cart for {} {}", productId, quantity);
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void simulateAPI() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Map<String, Integer> getProducts() {
        return Map.copyOf(products);
    }
}
