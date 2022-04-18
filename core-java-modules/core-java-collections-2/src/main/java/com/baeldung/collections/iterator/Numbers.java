package com.baeldung.collections.iterator;

import java.util.Arrays;
import java.util.List;

class Numbers {

    private static final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    private Numbers() {
    }

    public static CustomIterator<Integer> iterator() {
        return new PrimeIterator();
    }

    private static class PrimeIterator implements CustomIterator<Integer> {

        private int currentPosition;

        private static boolean isPrime(int number) {
            for (int i = 2; i <= number / 2; ++i) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Integer next() {
            return numbers.get(currentPosition++);
        }

        @Override
        public boolean hasNext() {
            if (currentPosition > numbers.size()) {
                return false;
            }

            for (int i = currentPosition; i < numbers.size(); i++) {
                if (isPrime(numbers.get(i))) {
                    currentPosition = i;
                    return true;
                }
            }

            return false;
        }
    }
}
