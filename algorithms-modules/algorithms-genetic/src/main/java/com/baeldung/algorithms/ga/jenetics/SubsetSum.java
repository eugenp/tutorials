package com.baeldung.algorithms.ga.jenetics;

import static java.util.Objects.requireNonNull;

import java.util.Random;
import java.util.function.Function;

import org.jenetics.EnumGene;
import org.jenetics.Mutator;
import org.jenetics.PartiallyMatchedCrossover;
import org.jenetics.Phenotype;
import org.jenetics.engine.Codec;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.Problem;
import org.jenetics.engine.codecs;
import org.jenetics.engine.limit;
import org.jenetics.util.ISeq;
import org.jenetics.util.LCG64ShiftRandom;

public class SubsetSum implements Problem<ISeq<Integer>, EnumGene<Integer>, Integer> {

    private ISeq<Integer> basicSet;
    private int size;

    public SubsetSum(ISeq<Integer> basicSet, int size) {
        this.basicSet = requireNonNull(basicSet);
        this.size = size;
    }

    @Override
    public Function<ISeq<Integer>, Integer> fitness() {
        return subset -> Math.abs(subset.stream()
            .mapToInt(Integer::intValue)
            .sum());
    }

    @Override
    public Codec<ISeq<Integer>, EnumGene<Integer>> codec() {
        return codecs.ofSubSet(basicSet, size);
    }

    public static SubsetSum of(int n, int k, Random random) {
        return new SubsetSum(random.doubles()
            .limit(n)
            .mapToObj(d -> (int) ((d - 0.5) * n))
            .collect(ISeq.toISeq()), k);
    }

    public static void main(String[] args) {
        SubsetSum problem = of(500, 15, new LCG64ShiftRandom(101010));

        Engine<EnumGene<Integer>, Integer> engine = Engine.builder(problem)
            .minimizing()
            .maximalPhenotypeAge(5)
            .alterers(new PartiallyMatchedCrossover<>(0.4), new Mutator<>(0.3))
            .build();

        Phenotype<EnumGene<Integer>, Integer> result = engine.stream()
            .limit(limit.bySteadyFitness(55))
            .collect(EvolutionResult.toBestPhenotype());

        System.out.print(result);
    }

}