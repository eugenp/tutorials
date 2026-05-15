package com.baeldung.paimon;

enum MetricColumns {
    DEVICE_ID(0),
    METRICS_NAME(1),
    METRICS_VALUE(2),
    SOURCE(3),
    TIMESTAMP(4);

    private final int index;

    MetricColumns(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}