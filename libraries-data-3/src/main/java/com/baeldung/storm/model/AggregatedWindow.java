package com.baeldung.storm.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class AggregatedWindow {
    int sumOfOperations;
    long beginningTimestamp;
    long endTimestamp;

    public AggregatedWindow(int sumOfOperations, long beginningTimestamp, long endTimestamp) {
        this.sumOfOperations = sumOfOperations;
        this.beginningTimestamp = beginningTimestamp;
        this.endTimestamp = endTimestamp;
    }
}
