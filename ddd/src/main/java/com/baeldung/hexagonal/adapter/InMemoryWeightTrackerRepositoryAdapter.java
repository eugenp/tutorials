package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.model.WeightInTime;
import com.baeldung.hexagonal.core.port.WeightTrackerRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryWeightTrackerRepositoryAdapter implements WeightTrackerRepository {
    private final Map<Long, WeightInTime> inMemoryHistory = new TreeMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public void addWeight(float weightInKG, LocalDateTime date) {
        long id = sequence.incrementAndGet();
        inMemoryHistory.put(id, new WeightInTime(id, weightInKG, date));
    }

    @Override
    public List<WeightInTime> getWeightHistory() {
        return Collections.unmodifiableList(new LinkedList<>(inMemoryHistory.values()));
    }

    @Override
    public boolean deleteById(long id) {
        return inMemoryHistory.remove(id) != null;
    }
}
