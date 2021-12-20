package com.baeldung.application;

import com.baeldung.domain.model.CoffeeOrder;
import com.baeldung.domain.ports.OrderRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A virtual db repository that holds entries in memory.
 */
public class VirtualOrderRepositoryAdapter implements OrderRepository {

    private AtomicInteger incrementalId = new AtomicInteger(0);
    private Map<Integer, CoffeeOrder> virtualDBTable = new HashMap<>();

    @Override
    public int create(CoffeeOrder order) {
        order.setId(incrementalId.getAndIncrement());
        virtualDBTable.put(order.getId(), order);
        return order.getId();
    }

    @Override
    public Optional<CoffeeOrder> findById(int id) {
        return Optional.ofNullable(virtualDBTable.get(id));
    }

    @Override
    public Collection<CoffeeOrder> findAll() {
        return virtualDBTable.values();
    }

    @Override
    public void update(CoffeeOrder order) {
        if (!virtualDBTable.containsKey(order.getId())) {
            throw new IllegalStateException("Attempted update without row present");
        }
        virtualDBTable.put(order.getId(), order);
    }
}
