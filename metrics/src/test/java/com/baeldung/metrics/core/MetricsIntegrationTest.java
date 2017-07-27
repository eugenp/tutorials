package com.baeldung.metrics.core;

import com.codahale.metrics.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MetricsIntegrationTest {
    @Test
    public void whenMarkMeter_thenCorrectRates() throws InterruptedException {
        Meter meter = new Meter();
        long initCount = meter.getCount();

        assertThat(initCount, equalTo(0L));

        meter.mark();

        assertThat(meter.getCount(), equalTo(1L));

        meter.mark(20);

        assertThat(meter.getCount(), equalTo(21L));

        // not use assert for these rate values because they change every time when this test is run
        double meanRate = meter.getMeanRate();
        double oneMinRate = meter.getOneMinuteRate();
        double fiveMinRate = meter.getFiveMinuteRate();
        double fifteenMinRate = meter.getFifteenMinuteRate();
        System.out.println(meanRate);
        System.out.println(oneMinRate);
        System.out.println(fiveMinRate);
        System.out.println(fifteenMinRate);
    }

    @Test
    public void whenInitRatioGauge_thenCorrectRatio() {
        Gauge<Double> ratioGauge = new AttendanceRatioGauge(15, 20);

        assertThat(ratioGauge.getValue(), equalTo(0.75));
    }

    @Test
    public void whenUseCacheGauge_thenCorrectGauge() {
        Gauge<List<Long>> activeUsersGauge = new ActiveUsersGauge(15, TimeUnit.MINUTES);
        List<Long> expected = new ArrayList<Long>();
        expected.add(12L);

        assertThat(activeUsersGauge.getValue(), equalTo(expected));
    }

    @Test
    public void whenUseDerivativeGauge_thenCorrectGaugeFromBase() {
        Gauge<List<Long>> activeUsersGauge = new ActiveUsersGauge(15, TimeUnit.MINUTES);
        Gauge<Integer> activeUserCountGauge = new ActiveUserCountGauge(activeUsersGauge);

        assertThat(activeUserCountGauge.getValue(), equalTo(1));
    }

    @Test
    public void whenIncDecCounter_thenCorrectCount() {
        Counter counter = new Counter();
        long initCount = counter.getCount();

        assertThat(initCount, equalTo(0L));

        counter.inc();

        assertThat(counter.getCount(), equalTo(1L));

        counter.inc(11);

        assertThat(counter.getCount(), equalTo(12L));

        counter.dec();

        assertThat(counter.getCount(), equalTo(11L));

        counter.dec(6);

        assertThat(counter.getCount(), equalTo(5L));
    }

    @Test
    public void whenUpdateHistogram_thenCorrectDistributionData() {
        Histogram histogram = new Histogram(new UniformReservoir());
        histogram.update(5);
        long count1 = histogram.getCount();

        assertThat(count1, equalTo(1L));

        Snapshot snapshot1 = histogram.getSnapshot();
        assertThat(snapshot1.getValues().length, equalTo(1));
        assertThat(snapshot1.getValues()[0], equalTo(5L));
        assertThat(snapshot1.getMax(), equalTo(5L));
        assertThat(snapshot1.getMin(), equalTo(5L));
        assertThat(snapshot1.getMean(), equalTo(5.0));
        assertThat(snapshot1.getMedian(), equalTo(5.0));
        assertThat(snapshot1.getStdDev(), equalTo(0.0));
        assertThat(snapshot1.get75thPercentile(), equalTo(5.0));
        assertThat(snapshot1.get95thPercentile(), equalTo(5.0));
        assertThat(snapshot1.get98thPercentile(), equalTo(5.0));
        assertThat(snapshot1.get99thPercentile(), equalTo(5.0));
        assertThat(snapshot1.get999thPercentile(), equalTo(5.0));

        histogram.update(20);
        long count2 = histogram.getCount();

        assertThat(count2, equalTo(2L));

        Snapshot snapshot2 = histogram.getSnapshot();

        assertThat(snapshot2.getValues().length, equalTo(2));
        assertThat(snapshot2.getValues()[0], equalTo(5L));
        assertThat(snapshot2.getValues()[1], equalTo(20L));
        assertThat(snapshot2.getMax(), equalTo(20L));
        assertThat(snapshot2.getMin(), equalTo(5L));
        assertThat(snapshot2.getMean(), equalTo(12.5));
        assertThat(snapshot2.getMedian(), equalTo(12.5));
        assertEquals(10.6, snapshot2.getStdDev(), 0.1);
        assertThat(snapshot2.get75thPercentile(), equalTo(20.0));
        assertThat(snapshot2.get95thPercentile(), equalTo(20.0));
        assertThat(snapshot2.get98thPercentile(), equalTo(20.0));
        assertThat(snapshot2.get99thPercentile(), equalTo(20.0));
        assertThat(snapshot2.get999thPercentile(), equalTo(20.0));
    }

    @Test
    public void whenUseTimer_thenCorrectTimerContexts() throws InterruptedException {
        Timer timer = new Timer();
        Timer.Context context1 = timer.time();
        TimeUnit.SECONDS.sleep(5);

        long elapsed1 = context1.stop();

        assertEquals(5000000000L, elapsed1, 1000000000);
        assertThat(timer.getCount(), equalTo(1L));
        assertEquals(0.2, timer.getMeanRate(), 0.2);

        Timer.Context context2 = timer.time();
        TimeUnit.SECONDS.sleep(2);
        context2.close();

        assertThat(timer.getCount(), equalTo(2L));
        assertEquals(0.3, timer.getMeanRate(), 0.2);
    }
}
