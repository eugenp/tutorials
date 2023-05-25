package com.baeldung.date;

public class Date {

    private long currentTimeMillis;

    public Date() {
        this(System.currentTimeMillis());
    }

    public Date(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    public long getTime() {
        return currentTimeMillis;
    }
}
