package com.baeldung.algorithms.ga.jenetics;

import java.util.function.Function;

import org.jenetics.BitChromosome;
import org.jenetics.BitGene;
import org.jenetics.Genotype;

public class KnapsackFF implements Function<Genotype<BitGene>, Double> {
    private KnapsackItem[] items;
    private double size;

    public KnapsackFF(KnapsackItem[] items, double size) {
        this.items = items;
        this.size = size;
    }

    @Override
    public Double apply(Genotype<BitGene> gt) {
        KnapsackItem sum = ((BitChromosome) gt.getChromosome()).ones()
            .mapToObj(i -> items[i])
            .collect(KnapsackItem.toSum());
        return sum.size <= this.size ? sum.value : 0;
    }
}
