package com.baeldung.stackoverflowerror;

/**
 * @author zn.wang
 */
public class UnintendedInfiniteRecursion {
    public int calculateFactorial(int number) {
        return number * calculateFactorial(number - 1);
    }
}
