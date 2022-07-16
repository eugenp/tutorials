package com.baeldung.metrics.micrometer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import io.micrometer.atlas.AtlasMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.distribution.HistogramSnapshot;
import io.micrometer.core.instrument.distribution.ValueAtPercentile;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.netflix.spectator.atlas.AtlasConfig;

/**
 * @author aiet
 */
public class MicrometerAtlasIntegrationManualTest {

    private AtlasConfig atlasConfig;

    @Before
    public void init() {
        atlasConfig = new AtlasConfig() {

            @Override
            public Duration step() {
                return Duration.ofSeconds(10);
            }

            @Override
            public String get(String k) {
                return null;
            }
        };
    }

    @Test
    public void givenCompositeRegistries_whenRecordMeter_thenAllRegistriesRecorded() {
        CompositeMeterRegistry compositeRegistry = new CompositeMeterRegistry();

        SimpleMeterRegistry oneSimpleMeter = new SimpleMeterRegistry();
        AtlasMeterRegistry atlasMeterRegistry = new AtlasMeterRegistry(atlasConfig, Clock.SYSTEM);

        compositeRegistry.add(oneSimpleMeter);
        compositeRegistry.add(atlasMeterRegistry);

        compositeRegistry.gauge("baeldung.heat", 90);

        Optional<Gauge> oneGauge = Optional.ofNullable(oneSimpleMeter
          .find("baeldung.heat")
          .gauge());
        assertTrue(oneGauge.isPresent());
        Iterator<Measurement> measurements = oneGauge
          .get()
          .measure()
          .iterator();

        assertTrue(measurements.hasNext());
        assertThat(measurements
          .next()
          .getValue(), equalTo(90.00));

        Optional<Gauge> atlasGauge = Optional.ofNullable(atlasMeterRegistry
          .find("baeldung.heat")
          .gauge());
        assertTrue(atlasGauge.isPresent());
        Iterator<Measurement> anotherMeasurements = atlasGauge
          .get()
          .measure()
          .iterator();

        assertTrue(anotherMeasurements.hasNext());
        assertThat(anotherMeasurements
          .next()
          .getValue(), equalTo(90.00));
    }

    @Test
    public void givenGlobalRegistry_whenIncrementAnywhere_thenCounted() {
        class CountedObject {
            private CountedObject() {
                Metrics
                  .counter("objects.instance")
                  .increment(1.0);
            }
        }
        Metrics.addRegistry(new SimpleMeterRegistry());

        Metrics
          .counter("objects.instance")
          .increment();
        new CountedObject();

        Optional<Counter> counterOptional = Optional.ofNullable(Metrics.globalRegistry
          .find("objects.instance")
          .counter());

        assertTrue(counterOptional.isPresent());
        assertEquals(counterOptional
          .get()
          .count() , 2.0, 0.0);
    }

    @Test
    public void givenCounter_whenIncrement_thenValueChanged() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        Counter counter = Counter
          .builder("objects.instance")
          .description("indicates instance count of the object")
          .tags("dev", "performance")
          .register(registry);

        counter.increment(2.0);
        assertEquals(counter.count(), 2, 0);

        counter.increment(-1);
        assertEquals(counter.count(), 1, 0);
    }

    @Test
    public void givenTimer_whenWrapTasks_thenTimeRecorded() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        Timer timer = registry.timer("app.event");
        timer.record(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(15);
            } catch (InterruptedException ignored) {
            }
        });

        timer.record(30, TimeUnit.MILLISECONDS);

        assertEquals(2, timer.count(), 0);

        assertThat(timer.totalTime(TimeUnit.MILLISECONDS)).isBetween(40.0, 55.0);
    }

    @Test
    public void givenLongTimer_whenRunTasks_thenTimerRecorded() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        LongTaskTimer longTaskTimer = LongTaskTimer
          .builder("3rdPartyService")
          .register(registry);

        LongTaskTimer.Sample currentTaskId = longTaskTimer.start();
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException ignored) {
        }
        long timeElapsed = currentTaskId.stop();

        assertEquals(2L, timeElapsed/((int) 1e6),1L);
    }

    @Test
    public void givenGauge_whenMeterListSize_thenCurrentSizeMonitored() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        List<String> list = new ArrayList<>(4);
        Gauge gauge = Gauge
          .builder("cache.size", list, List::size)
          .register(registry);

        assertEquals(gauge.value(), 0.0, 0.0);

        list.add("1");
        assertEquals(gauge.value(), 1.0, 0.0);
    }

    @Test
    public void givenDistributionSummary_whenRecord_thenSummarized() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        DistributionSummary distributionSummary = DistributionSummary
          .builder("request.size")
          .baseUnit("bytes")
          .register(registry);
        distributionSummary.record(3);
        distributionSummary.record(4);
        distributionSummary.record(5);

        assertEquals(3, distributionSummary.count(), 0);
        assertEquals(12, distributionSummary.totalAmount(), 0);
    }

    @Test
    public void givenTimer_whenEnrichWithPercentile_thenPercentilesComputed() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        Timer timer = Timer
          .builder("test.timer")
          .publishPercentiles(0.3, 0.5, 0.95)
          .publishPercentileHistogram()
          .register(registry);

        timer.record(2, TimeUnit.SECONDS);
        timer.record(2, TimeUnit.SECONDS);
        timer.record(3, TimeUnit.SECONDS);
        timer.record(4, TimeUnit.SECONDS);
        timer.record(8, TimeUnit.SECONDS);
        timer.record(13, TimeUnit.SECONDS);

        Map<Double, Double> expectedMicrometer = new TreeMap<>();
        expectedMicrometer.put(0.3, 1946.157056);
        expectedMicrometer.put(0.5, 3019.89888);
        expectedMicrometer.put(0.95, 13354.663936);

        Map<Double, Double> actualMicrometer = new TreeMap<>();
        ValueAtPercentile[] percentiles = timer.takeSnapshot().percentileValues();
        for (ValueAtPercentile percentile : percentiles) {
            actualMicrometer.put(percentile.percentile(), percentile.value(TimeUnit.MILLISECONDS));
        }

        assertEquals(expectedMicrometer, actualMicrometer);
    }

    @Test
    public void givenDistributionSummary_whenEnrichWithHistograms_thenDataAggregated() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        DistributionSummary hist = DistributionSummary
          .builder("summary")
          .serviceLevelObjectives(1, 10, 5)
          .register(registry);

        hist.record(3);
        hist.record(8);
        hist.record(20);
        hist.record(40);
        hist.record(13);
        hist.record(26);

        Map<Integer, Double> expectedMicrometer = new TreeMap<>();
        expectedMicrometer.put(1,0D);
        expectedMicrometer.put(10,2D);
        expectedMicrometer.put(5,1D);

        Map<Integer, Double> actualMicrometer = new TreeMap<>();
        HistogramSnapshot snapshot = hist.takeSnapshot();
        Arrays.stream(snapshot.histogramCounts()).forEach(p -> {
            actualMicrometer.put((Integer.valueOf((int) p.bucket())), p.count());
        });

       assertEquals(expectedMicrometer, actualMicrometer);
    }

    @Test
    public void givenTimer_whenEnrichWithTimescaleHistogram_thenTimeScaleDataCollected() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        Duration[] durations = {Duration.ofMillis(25), Duration.ofMillis(300), Duration.ofMillis(600)};
        Timer timer = Timer
          .builder("timer")
          .sla(durations)
           .publishPercentileHistogram()
          .register(registry);

        timer.record(1000, TimeUnit.MILLISECONDS);
        timer.record(23, TimeUnit.MILLISECONDS);
        timer.record(450, TimeUnit.MILLISECONDS);
        timer.record(341, TimeUnit.MILLISECONDS);
        timer.record(500, TimeUnit.MILLISECONDS);

        Map<Double, Double> expectedMicrometer = new TreeMap<>();
        expectedMicrometer.put(2.5E7,1D);
        expectedMicrometer.put(3.0E8,1D);
        expectedMicrometer.put(6.0E8,4D);

        Map<Double, Double> actualMicrometer = new TreeMap<>();
        HistogramSnapshot snapshot = timer.takeSnapshot();
        Arrays.stream(snapshot.histogramCounts()).forEach(p -> {
          actualMicrometer.put((Double.valueOf((int) p.bucket())), p.count());
        });

        assertEquals(expectedMicrometer, actualMicrometer);
    }

}
