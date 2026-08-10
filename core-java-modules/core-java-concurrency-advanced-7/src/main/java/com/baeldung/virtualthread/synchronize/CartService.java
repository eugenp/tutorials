package com.baeldung.virtualthread.synchronize;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final Map<String, Integer> products;
    private final Map<String, Object> locks = new ConcurrentHashMap<>();

    public CartService() {
        this.products = new HashMap<>();
    }

    public void update(String productId, int quantity) {
        Object lock = locks.computeIfAbsent(productId, k -> new Object());

        synchronized (lock) {
            simulateAPI();
            products.merge(productId, quantity, Integer::sum);
        }

        LOGGER.info("Updated Cart for {} {}", productId, quantity);
    }

    public Map<String, Integer> getProducts() {
        return Map.copyOf(products);
    }

    private void simulateAPI() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
