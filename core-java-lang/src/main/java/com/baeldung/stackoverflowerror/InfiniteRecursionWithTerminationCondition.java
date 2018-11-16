package com.baeldung.stackoverflowerror;

public class InfiniteRecursionWithTerminationCondition {
    public int calculateFactorial(final int number) {
        return number == 1 ? 1 : number * calculateFactorial(number - 1);
    }
}
