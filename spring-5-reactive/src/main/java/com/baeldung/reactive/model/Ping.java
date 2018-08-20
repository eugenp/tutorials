package com.baeldung.reactive.model;

import java.time.Instant;

public class Ping {
    private final Instant pingTime;

    public Ping() {
        this.pingTime = Instant.now();
    }

    public Instant getPingTime() {
        return this.pingTime;
    }

    @Override
    public String toString() {
        return "DateTime: " + this.pingTime.toString();
    }
}
