package com.baeldung.stackoverflowerror;

public class UnintendedInfiniteRecursion {
    public int calculateFactorial(int number) {
        return number * calculateFactorial(number - 1);
    }
}
