package com.baeldung.uniquerng;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class BigUniqueRng implements Iterator<Integer> {

    private Random random = new Random();
    private Set<Integer> generated = new LinkedHashSet<>();

    public BigUniqueRng(int size, int max) {
        while (generated.size() < size) {
            Integer next = random.nextInt(max);
            generated.add(next);
        }
    }

    @Override
    public Integer next() {
        if (!hasNext())
            throw new NoSuchElementException();

        Iterator<Integer> iterator = generated.iterator();
        Integer next = iterator.next();
        iterator.remove();
        return next;
    }

    @Override
    public boolean hasNext() {
        return !generated.isEmpty();
    }
}
