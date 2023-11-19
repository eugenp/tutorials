package com.baeldung.samples;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Statistic;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;

import java.util.Optional;
import java.util.stream.StreamSupport;

public class SimpleObservationApplication {

    // we can run this as a simple command line application
    public static void main(String[] args) {
        // create registry
        final var observationRegistry = ObservationRegistry.create();
        // create meter registry and observation handler
        final var meterRegistry = new SimpleMeterRegistry();
        final var meterObservationHandler = new DefaultMeterObservationHandler(meterRegistry);
        // create simple logging observation handler
        final var loggingObservationHandler = new ObservationTextPublisher(System.out::println);
        // register observation handlers
        observationRegistry
          .observationConfig()
          .observationHandler(meterObservationHandler)
          .observationHandler(loggingObservationHandler);
        // make an observation
        Observation.Context context = new Observation.Context();
        String observationName = "obs1";
        Observation observation = Observation
          .createNotStarted(observationName, () -> context, observationRegistry)
          .lowCardinalityKeyValue("gender", "male")
          .highCardinalityKeyValue("age", "41");

        for (int i = 0; i < 10; i++) {
            observation.observe(SimpleObservationApplication::doSomeAction);
        }

        meterRegistry.getMeters().forEach(m -> {
            System.out.println(m.getId() + "\n============");
            m.measure().forEach(ms -> System.out.println(ms.getValue() + " [" + ms.getStatistic() + "]"));
            System.out.println("----------------------------");
        });
        Optional<Double> maximumDuration = meterRegistry.getMeters().stream()
          .filter(m -> "obs1".equals(m.getId().getName()))
          .flatMap(m -> StreamSupport.stream(m.measure().spliterator(), false))
          .filter(ms -> ms.getStatistic() == Statistic.MAX)
          .findFirst()
          .map(Measurement::getValue);

        System.out.println(maximumDuration);
    }

    private static void doSomeAction() {
        try {
            Thread.sleep(Math.round(Math.random() * 1000));
            System.out.println("Hello World!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
