package com.baeldung.reactive.model;

import java.time.LocalDateTime;

public class CpuUsageEvent {

    private Double cpuUsage;

    private LocalDateTime time;

    public CpuUsageEvent() {

    }

    public CpuUsageEvent(Double cpuUsage, LocalDateTime time) {
        this.cpuUsage = cpuUsage;
        this.time = time;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CpuUsageEvent{" + "cpuUsage=" + cpuUsage + "% " + ", time=" + time + '}';
    }
}
