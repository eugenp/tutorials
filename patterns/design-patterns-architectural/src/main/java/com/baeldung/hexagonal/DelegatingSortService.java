package com.baeldung.hexagonal;

import java.time.Duration;
import java.time.Instant;

public class DelegatingSortService implements SortService {

    private SortImplementationPort sortImpl;
    private SortResultRepositoryPort sortResultRepo;

    public DelegatingSortService(SortImplementationPort sortImpl, SortResultRepositoryPort sortResultRepo) {
        this.sortImpl = sortImpl;
        this.sortResultRepo = sortResultRepo;
    }

    @Override
    public int[] sort(int[] items) {
        if (items.length > 1000000) {
            throw new IllegalStateException("Max length supported exceeded.");
        }

        Instant start = Instant.now();

        int[] sortedItems = sortImpl.sort(items);

        Instant end = Instant.now();

        sortResultRepo.store(new SortResult(sortImpl.getName(), sortedItems, Duration.between(start, end)));
        
        return sortedItems;
    }
}
