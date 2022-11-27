package com.baeldung.samples;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

public class SimpleObservationApplication {

    // we can run this as a simple command line application
    public static void main(String[] args) {

        ObservationRegistry registry = ObservationRegistry.create();
        MeterRegistry meterRegistry = new SimpleMeterRegistry();
        registry
          .observationConfig()
          .observationHandler(new DefaultMeterObservationHandler(meterRegistry));
        // make an observation
        Observation.Context context = new Observation.Context();
        Observation observation = Observation
          .createNotStarted("obs1", () -> context, registry)
          .lowCardinalityKeyValue("gender", "male")
          .highCardinalityKeyValue("age", "41");

        for (int i = 0; i < 10; i++) {
            final int index = i;
            observation.observe(() -> {
                try {
                    Thread.sleep(Math.round(Math.random() * 1000));
                    System.out.println("Hello World " + index + "!");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        meterRegistry.getMeters().forEach(m -> {
            System.out.println(m.getId() + "\n============");
            m.measure().forEach(ms -> System.out.println(ms.getValue() + " [" + ms.getStatistic() + "]"));
            System.out.println("----------------------------");
        });
        System.out.println();
    }

}
