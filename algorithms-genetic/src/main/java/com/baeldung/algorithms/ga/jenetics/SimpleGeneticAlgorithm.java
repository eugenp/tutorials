package com.baeldung.algorithms.ga.jenetics;

import org.jenetics.BitChromosome;
import org.jenetics.BitGene;
import org.jenetics.Genotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;

public class SimpleGeneticAlgorithm {

    private static Integer eval(Genotype<BitGene> gt) {
        return gt.getChromosome()
            .as(BitChromosome.class)
            .bitCount();
    }

    public static void main(String[] args) {
        Factory<Genotype<BitGene>> gtf = Genotype.of(BitChromosome.of(10, 0.5));
        System.out.println("Before the evolution:\n" + gtf);

        Engine<BitGene, Integer> engine = Engine.builder(SimpleGeneticAlgorithm::eval, gtf)
            .build();

        Genotype<BitGene> result = engine.stream()
            .limit(500)
            .collect(EvolutionResult.toBestGenotype());

        System.out.println("After the evolution:\n" + result);

    }

}
