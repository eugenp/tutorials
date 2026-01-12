package com.baeldung.micrometer.references;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootTest(classes = Application.class)
class GaugeReferenceIntegrationTest {

    @Autowired
    private MeterRegistry registry;

    @Test
    void whenWeakReference_thenGaugeShowsNaNAfterGC() throws InterruptedException {
        Gauge weakGauge = registry.find("tasks.active.weak").gauge();
        Gauge strongGauge = registry.find("tasks.active.strong").gauge();

        assertEquals(10.0, weakGauge.value(), "Weak gauge should initially show 10");
        assertEquals(20.0, strongGauge.value(), "Strong gauge should initially show 20");

        System.gc();
        Thread.sleep(1000);

        assertTrue(Double.isNaN(weakGauge.value()), "Weak gauge should show NaN after GC");
        assertEquals(20.0, strongGauge.value(), "Strong gauge should still show 20 after GC");
    }
}