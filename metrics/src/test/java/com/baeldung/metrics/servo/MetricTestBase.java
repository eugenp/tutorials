package com.baeldung.metrics.servo;

import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MemoryMetricObserver;
import com.netflix.servo.publish.MetricFilter;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;
import org.junit.After;
import org.junit.Before;

import static java.util.concurrent.TimeUnit.SECONDS;

abstract class MetricTestBase {

    MemoryMetricObserver observer;

    @Before
    public void prepareScheduler() {
        System.setProperty("servo.pollers", "1000");
        observer = new MemoryMetricObserver();
        PollScheduler
          .getInstance()
          .start();
        MetricFilter metricFilter = new BasicMetricFilter(true);
        PollRunnable task = new PollRunnable(new MonitorRegistryMetricPoller(), metricFilter, observer);
        PollScheduler
          .getInstance()
          .addPoller(task, 1, SECONDS);
    }

    @After
    public void stopScheduler() {
        if (PollScheduler
          .getInstance()
          .isStarted()) {
            PollScheduler
              .getInstance()
              .stop();
        }
    }
}
