package com.baeldung.annotations;

public class IntervalUsage {

    @Interval(hour = 17)
    @Interval(hour = 13)
    void doPeriodicCleanup() {
    }
}
