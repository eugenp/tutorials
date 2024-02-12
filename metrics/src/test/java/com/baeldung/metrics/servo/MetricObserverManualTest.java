package com.baeldung.metrics.servo;

import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.Metric;
import com.netflix.servo.monitor.BasicGauge;
import com.netflix.servo.monitor.DynamicCounter;
import com.netflix.servo.monitor.Gauge;
import com.netflix.servo.monitor.MonitorConfig;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static com.netflix.servo.annotations.DataSourceType.GAUGE;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MetricObserverManualTest extends MetricTestBase {

    @Test
    public void givenMetrics_whenRegister_thenMonitored() throws InterruptedException {
        Gauge<Double> gauge = new BasicGauge<>(MonitorConfig
          .builder("test")
          .build(), () -> 2.32);
        assertEquals(2.32, gauge.getValue(), 0.01);

        DefaultMonitorRegistry
          .getInstance()
          .register(gauge);

        for (int i = 0; i < 2; i++) {
            SECONDS.sleep(1);
        }

        List<List<Metric>> metrics = observer.getObservations();
        assertThat(metrics, hasSize(greaterThanOrEqualTo(2)));

        Iterator<List<Metric>> metricIterator = metrics.iterator();
        //skip first empty observation
        metricIterator.next();
        while (metricIterator.hasNext()) {
            assertThat(metricIterator.next(), hasItem(allOf(hasProperty("config", hasProperty("tags", hasItem(GAUGE))), hasProperty("value", is(2.32)))));
        }

    }

    @Test
    public void givenMetrics_whenRegisterDynamically_thenMonitored() throws Exception {
        for (int i = 0; i < 2; i++) {
            DynamicCounter.increment("monitor-name", "tag-key", "tag-value");
            SECONDS.sleep(1);
        }

        List<List<Metric>> metrics = observer.getObservations();
        assertThat(metrics, hasSize(greaterThanOrEqualTo(2)));

        Iterator<List<Metric>> metricIterator = metrics.iterator();
        //skip first empty observation
        metricIterator.next();
        while (metricIterator.hasNext()) {
            assertThat(metricIterator.next(), hasItem(hasProperty("value", greaterThanOrEqualTo(1.0))));
        }
    }

}
