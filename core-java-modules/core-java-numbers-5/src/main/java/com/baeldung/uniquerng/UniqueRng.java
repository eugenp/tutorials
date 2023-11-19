package com.baeldung.uniquerng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class UniqueRng implements Iterator<Integer> {

    private int size;
    private List<Integer> numbers = new ArrayList<>();

    public UniqueRng(int size, boolean zeroBased) {
        this.size = size;
        int start = (zeroBased ? 0 : 1);
        int limit = (zeroBased ? size - 1 : size);

        for (int i = start; i <= limit; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
    }

    @Override
    public Integer next() {
        if (!hasNext())
            throw new NoSuchElementException();

        return numbers.remove(0);
    }

    @Override
    public boolean hasNext() {
        return !numbers.isEmpty();
    }

    public int getSize() {
        return size;
    }
}
