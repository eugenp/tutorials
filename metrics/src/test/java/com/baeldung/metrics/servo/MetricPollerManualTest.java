package com.baeldung.metrics.servo;

import com.netflix.servo.Metric;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.JvmMetricPoller;
import com.netflix.servo.publish.MemoryMetricObserver;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;
import org.junit.Test;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class MetricPollerManualTest {

    @Test
    public void givenJvmPoller_whenMonitor_thenDataCollected() throws Exception {
        MemoryMetricObserver observer = new MemoryMetricObserver();
        PollRunnable pollRunnable = new PollRunnable(new JvmMetricPoller(), new BasicMetricFilter(true), observer);
        PollScheduler
          .getInstance()
          .start();
        PollScheduler
          .getInstance()
          .addPoller(pollRunnable, 1, SECONDS);

        SECONDS.sleep(1);

        PollScheduler
          .getInstance()
          .stop();
        List<List<Metric>> metrics = observer.getObservations();
        assertThat(metrics, hasSize(greaterThanOrEqualTo(1)));

        List<String> args = metrics
          .stream()
          .filter(m -> !m.isEmpty())
          .flatMap(ms -> ms
            .stream()
            .map(m -> m
              .getConfig()
              .getName()))
          .collect(toList());
        assertThat(args, hasItems("loadedClassCount", "initUsage", "maxUsage", "threadCount"));
    }

}
