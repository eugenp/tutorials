package com.baeldung.collections.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class Numbers {

    private static final List<Integer> NUMBER_LIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    private Numbers() {
    }

    public static Iterator<Integer> iterator() {
        return new PrimeIterator();
    }

    private static class PrimeIterator implements Iterator<Integer> {

        private int cursor;

        @Override
        public Integer next() {
            exist(cursor);
            return NUMBER_LIST.get(cursor++);
        }

        private void exist(int current) {
            if (current >= NUMBER_LIST.size()) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public boolean hasNext() {
            if (cursor > NUMBER_LIST.size()) {
                return false;
            }

            for (int i = cursor; i < NUMBER_LIST.size(); i++) {
                if (isPrime(NUMBER_LIST.get(i))) {
                    cursor = i;
                    return true;
                }
            }

            return false;
        }

        private boolean isPrime(int number) {
            for (int i = 2; i <= number / 2; ++i) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
