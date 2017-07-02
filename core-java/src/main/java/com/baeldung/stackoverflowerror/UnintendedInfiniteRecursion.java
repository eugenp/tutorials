package com.baeldung.stackoverflowerror;

class UnintendedInfiniteRecursion {
    public int calculateFactorial(int number) {
        return number * calculateFactorial(number - 1);
    }
}
