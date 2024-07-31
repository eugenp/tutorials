package com.baeldung.metrics.core;

import java.util.List;

import com.codahale.metrics.DerivativeGauge;
import com.codahale.metrics.Gauge;

public class ActiveUserCountGauge extends DerivativeGauge<List<Long>, Integer> {
    public ActiveUserCountGauge(Gauge<List<Long>> base) {
        super(base);
    }

    @Override
    protected Integer transform(List<Long> value) {
        return value.size();
    }
}
