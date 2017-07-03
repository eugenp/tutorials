package com.baeldung.algorithms.ga.jenetics;

import java.util.Random;
import java.util.stream.Collector;

import org.jenetics.util.RandomRegistry;

public class KnapsackItem {

    public double size;
    public double value;

    private KnapsackItem(double size, double value) {
        this.size = size;
        this.value = value;
    }

    static KnapsackItem random() {
        Random r = RandomRegistry.getRandom();
        return new KnapsackItem(r.nextDouble() * 100, r.nextDouble() * 100);
    }

    static Collector<KnapsackItem, ?, KnapsackItem> toSum() {
        return Collector.of(() -> new double[2], (a, b) -> {
            a[0] += b.size;
            a[1] += b.value;
        } , (a, b) -> {
            a[0] += b[0];
            a[1] += b[1];
            return a;
        } , r -> new KnapsackItem(r[0], r[1]));
    }

}
