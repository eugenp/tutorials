package com.baeldung.streams.parallelstream;

import java.util.Spliterator;
import java.util.function.Consumer;

public class BookSpliterator<T> implements Spliterator<T> {
    private final Object[] books;
    private int startIndex;
    public BookSpliterator(Object[] books, int startIndex) {
        this.books = books;
        this.startIndex = startIndex;
    }

    @Override
    public Spliterator<T> trySplit() {
        // Always Assuming that the source is too small to split, returning null
        return null;
    }

    // Other overridden methods such as tryAdvance(), estimateSize() etc

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (startIndex < books.length) {
            startIndex += 2;
            return true;
        }
        return false;
    }

    @Override
    public long estimateSize() {
        return books.length - startIndex;
    }

    @Override
    public int characteristics() {
        return CONCURRENT;
    }
}