package com.baeldung.inlining;

public class ConsecutiveNumbersSum {

    private long totalSum;
    private int totalNumbers;

    public ConsecutiveNumbersSum(int totalNumbers) {
        this.totalNumbers = totalNumbers;
    }

    public long getTotalSum() {
        totalSum = 0;
        for (int i = 1; i <= totalNumbers; i++) {
            totalSum += i;
        }
        return totalSum;
    }
}
