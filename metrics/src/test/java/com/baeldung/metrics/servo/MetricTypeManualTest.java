package com.baeldung.metrics.servo;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.BasicGauge;
import com.netflix.servo.monitor.BasicInformational;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.BucketConfig;
import com.netflix.servo.monitor.BucketTimer;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.Gauge;
import com.netflix.servo.monitor.MaxGauge;
import com.netflix.servo.monitor.Monitor;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.monitor.PeakRateCounter;
import com.netflix.servo.monitor.StatsTimer;
import com.netflix.servo.monitor.StepCounter;
import com.netflix.servo.monitor.Stopwatch;
import com.netflix.servo.stats.StatsConfig;

public class MetricTypeManualTest {

    @Test
    public void givenDefaultCounter_whenManipulate_thenCountValid() {
        Counter counter = Monitors.newCounter("test");
        assertEquals("counter should start with 0", 0, counter
          .getValue()
          .intValue());
        counter.increment();
        assertEquals("counter should have increased by 1", 1, counter
          .getValue()
          .intValue());
        counter.increment(-1);
        assertEquals("counter should have decreased by 1", 0, counter
          .getValue()
          .intValue());
    }

    @Test
    public void givenBasicCounter_whenManipulate_thenCountValid() {
        Counter counter = new BasicCounter(MonitorConfig
          .builder("test")
          .build());
        assertEquals("counter should start with 0", 0, counter
          .getValue()
          .intValue());
        counter.increment();
        assertEquals("counter should have increased by 1", 1, counter
          .getValue()
          .intValue());
        counter.increment(-1);
        assertEquals("counter should have decreased by 1", 0, counter
          .getValue()
          .intValue());
    }

    @Ignore
    @Test
    public void givenStepCounter_whenManipulate_thenRateValid() throws Exception {
        System.setProperty("servo.pollers", "1000");
        Counter counter = new StepCounter(MonitorConfig
          .builder("test")
          .build());
        assertEquals("counter should start with rate 0.0", 0.0, counter.getValue());

        counter.increment();
        SECONDS.sleep(1);

        assertEquals("counter rate should have increased to 1.0", 1.0, counter.getValue());
    }

    @Test
    public void givenPeakRateCounter_whenManipulate_thenPeakRateReturn() throws Exception {
        Counter counter = new PeakRateCounter(MonitorConfig
          .builder("test")
          .build());
        assertEquals("counter should start with 0", 0, counter
          .getValue()
          .intValue());

        counter.increment();
        SECONDS.sleep(1);
        counter.increment();
        counter.increment();

        assertEquals("peak rate should have be 2", 2, counter
          .getValue()
          .intValue());
    }

    @Test
    public void givenTimer_whenExecuteTask_thenTimerUpdated() throws Exception {
        BasicTimer timer = new BasicTimer(MonitorConfig
          .builder("test")
            .build(), MILLISECONDS);

        Stopwatch stopwatch = timer.start();
        SECONDS.sleep(1);
        timer.record(2, SECONDS);
        stopwatch.stop();

        assertEquals("timer should count 1 second", 1000, timer
          .getValue()
          .intValue(),1000);        
        assertEquals("timer should count 3 second in total", 3000, timer.getTotalTime()
            .intValue(),1000);
        assertEquals("timer should record 2 updates", 2, timer
          .getCount()
          .intValue());

        assertEquals("timer should have max 2", 2000, timer.getMax(), 0.01);
    }

    @Test
    public void givenBucketTimer_whenRecord_thenStatsCalculated() throws Exception {
        BucketTimer timer = new BucketTimer(MonitorConfig
          .builder("test")
          .build(), new BucketConfig.Builder()
          .withBuckets(new long[]{2L, 5L})
          .withTimeUnit(SECONDS)
          .build(), SECONDS);
        timer.record(3);
        timer.record(6);

        assertEquals("timer should count 9 seconds in total", 9, timer
          .getTotalTime()
          .intValue());

        final Map<String, Long> metricMap = timer
          .getMonitors()
          .stream()
          .filter(monitor -> monitor
            .getConfig()
            .getTags()
            .containsKey("servo.bucket"))
          .collect(toMap(monior -> getMonitorTagValue(monior, "servo.bucket"), monitor -> (Long) monitor.getValue()));

        assertThat(metricMap, allOf(hasEntry("bucket=2s", 0L), hasEntry("bucket=5s", 1L), hasEntry("bucket=overflow", 1L)));
    }

    private static String getMonitorTagValue(Monitor monitor, String tagKey) {
        return monitor
          .getConfig()
          .getTags()
          .getTag(tagKey)
          .getValue();
    }

    @Test
    public void givenStatsTimer_whenExecuteTask_thenStatsCalculated() throws Exception {
        System.setProperty("netflix.servo", "1000");
        StatsTimer timer = new StatsTimer(MonitorConfig
          .builder("test")
          .build(), new StatsConfig.Builder()
          .withComputeFrequencyMillis(2000)
          .withPercentiles(new double[]{99.0, 95.0, 90.0})
          .withPublishMax(true)
          .withPublishMin(true)
          .withPublishCount(true)
          .withPublishMean(true)
          .withPublishStdDev(true)
          .withPublishVariance(true)
            .build(), MILLISECONDS);

        Stopwatch stopwatch = timer.start();
        SECONDS.sleep(1);
        timer.record(3, SECONDS);
        stopwatch.stop();

        stopwatch = timer.start();
        timer.record(6, SECONDS);
        SECONDS.sleep(2);
        stopwatch.stop();

        assertEquals("timer should count 12 seconds in total", 12000, timer.getTotalTime(),500);
        assertEquals("timer should count 12 seconds in total", 12000, timer.getTotalMeasurement(),500);
        assertEquals("timer should record 4 updates", 4, timer.getCount());
        assertEquals("stats timer value time-cost/update should be 2", 3000, timer
          .getValue()
          .intValue(),500);

        final Map<String, Number> metricMap = timer
          .getMonitors()
          .stream()
          .collect(toMap(monitor -> getMonitorTagValue(monitor, "statistic"), monitor -> (Number) monitor.getValue()));

        assertThat(metricMap.keySet(), containsInAnyOrder("count", "totalTime", "max", "min", "variance", "stdDev", "avg", "percentile_99", "percentile_95", "percentile_90"));
    }

    @Test
    public void givenGauge_whenCall_thenValueReturned() {
        Gauge<Double> gauge = new BasicGauge<>(MonitorConfig
          .builder("test")
          .build(), () -> 2.32);
        assertEquals(2.32, gauge.getValue(), 0.01);
    }

    @Test
    public void givenMaxGauge_whenUpdateMultipleTimes_thenMaxReturned() {
        MaxGauge gauge = new MaxGauge(MonitorConfig
          .builder("test")
          .build());
        assertEquals(0, gauge
          .getValue()
          .intValue());

        gauge.update(4);
        assertEquals(4, gauge.getCurrentValue(0));

        gauge.update(1);
        assertEquals(4, gauge.getCurrentValue(0));
    }

    @Test
    public void givenInformationalMonitor_whenRecord_thenInformationCollected() throws Exception {
        BasicInformational informational = new BasicInformational(MonitorConfig
          .builder("test")
          .build());
        informational.setValue("information collected");
        assertEquals("information collected", informational.getValue());
    }

}