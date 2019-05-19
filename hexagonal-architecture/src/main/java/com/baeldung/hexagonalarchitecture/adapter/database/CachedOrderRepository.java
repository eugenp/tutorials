package com.baeldung.hexagonalarchitecture.adapter.database;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalarchitecture.core.domain.Order;
import com.baeldung.hexagonalarchitecture.core.repository.OrderRepository;

@Repository
public class CachedOrderRepository implements OrderRepository {

    private final Map<Long, Order> cachedOrderStore = new ConcurrentHashMap<>();

    private AtomicLong orderIdSequence = new AtomicLong(0);

    @Override
    public Order getOrderById(Long id) {
        return cachedOrderStore.get(id);
    }

    @Override
    public Order createOrder(Order order) {
        order.setId(orderIdSequence.incrementAndGet());
        return updateStore(order.getId(), order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        return updateStore(id, order);
    }

    @Override
    public Order deleteOrder(Long id) {
        return cachedOrderStore.remove(id);
    }

    private Order updateStore(Long id, Order order) {
        cachedOrderStore.put(id, order);
        return cachedOrderStore.get(id);
    }

}
