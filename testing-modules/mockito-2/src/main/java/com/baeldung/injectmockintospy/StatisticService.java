package com.baeldung.injectmockintospy;

import lombok.Getter;

@Getter
public class StatisticService {

    private long todaysActions;

    public void calculateAdded() {
        todaysActions++;
    }

    public void calculateRemoved() {
        todaysActions--;
    }

    public void reset() {
        todaysActions = 0L;
    }

}
