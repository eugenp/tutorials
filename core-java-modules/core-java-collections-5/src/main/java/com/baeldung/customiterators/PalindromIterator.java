package com.baeldung.customiterators;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class PalindromIterator implements Iterator<String> {
    private final List<String> list;
    private int currentIndex;

    public PalindromIterator(List<String> list) {
        this.list = list;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        while (currentIndex < list.size()) {
            String currString = list.get(currentIndex);
            if (isPalindrome(currString)) {
                return true;
            }
            currentIndex++;
        }
        return false;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(currentIndex++);
    }

    private boolean isPalindrome(String input) {
        for (int i = 0; i < input.length() / 2; i++) {
            if (input.charAt(i) != input.charAt(input.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
