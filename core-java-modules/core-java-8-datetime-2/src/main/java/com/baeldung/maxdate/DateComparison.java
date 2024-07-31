package com.baeldung.maxdate;

import java.util.Date;

public class DateComparison {
    public int compareTodayWithMaxDate() {
        Date today = new Date();
        Date maxDate = new Date(Long.MAX_VALUE);

        int comparisonResult = today.compareTo(maxDate);
        return comparisonResult;
    }

    public static void main(String[] args) {
        DateComparison comparator = new DateComparison();
        System.out.println(comparator.compareTodayWithMaxDate());
    }
}