package com.baeldung.hexagonal;

public class ConsoleSortResultRepositoryAdapter implements SortResultRepositoryPort {

    @Override
    public void store(SortResult result) {
        int nb = result.getSortedItems().length;
        long ms = result.getDuration()
            .toMillis();
        String algo = result.getAlgo();

        System.out.println(String.format("Sorted %d items in %d ms using %s", nb, ms, algo));
    }
}
