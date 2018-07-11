package com.baeldung.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple Memory Stats Data structure.
 */
public class MemoryStats {
    private static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private long freeMemory;
    private long totalMemory;
    private String timeStamp = "";

    public long getFreeMemory() {
        return freeMemory;
    }

    public MemoryStats setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
        return this;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public MemoryStats setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
        return this;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public MemoryStats setTimeStamp(Date date) {
        this.timeStamp = SIMPLE_DATE_FORMATTER.format(date);
        return this;
    }

    @Override
    public String toString() {
        return "MemoryStats{" +
                "freeMemory=" + freeMemory +
                ", totalMemory=" + totalMemory +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
