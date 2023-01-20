package com.baeldung.streams.processing;

import static java.util.Spliterator.ORDERED;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomBatchIterator<T> implements Iterator<List<T>> {
    private final int batchSize;
    private List<T> currentBatch;
    private final Iterator<T> iterator;

    public CustomBatchIterator(Iterator<T> sourceIterator, int batchSize) {
        this.batchSize = batchSize;
        this.iterator = sourceIterator;
    }

    @Override
    public List<T> next() {
        return currentBatch;
    }

    @Override
    public boolean hasNext() {
        prepareNextBatch();
        return currentBatch != null && !currentBatch.isEmpty();
    }

    public static <T> Stream<List<T>> batchStreamOf(Stream<T> stream, int batchSize) {
        return stream(new CustomBatchIterator<>(stream.iterator(), batchSize));
    }

    private static <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, ORDERED), false);
    }

    private void prepareNextBatch() {
        currentBatch = new ArrayList<>(batchSize);
        while (iterator.hasNext() && currentBatch.size() < batchSize) {
            currentBatch.add(iterator.next());
        }
    }
}