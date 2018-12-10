package com.baeldung.algorithms.ga.jenetics;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.bySteadyFitness;

import java.util.stream.IntStream;

import org.jenetics.EnumGene;
import org.jenetics.Optimize;
import org.jenetics.PartiallyMatchedCrossover;
import org.jenetics.Phenotype;
import org.jenetics.SwapMutator;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionStatistics;
import org.jenetics.engine.codecs;

public class TravelingSalesman {

    private static final int STOPS = 50;
    private static final double[][] ADJACENCE = matrix(STOPS);

    private static double[][] matrix(int stops) {
        final double radius = 100.0;
        double[][] matrix = new double[stops][stops];

        for (int i = 0; i < stops; ++i) {
            for (int j = 0; j < stops; ++j) {
                matrix[i][j] = chord(stops, abs(i - j), radius);
            }
        }
        return matrix;
    }

    private static double chord(int stops, int i, double r) {
        return 2.0 * r * abs(sin(PI * i / stops));
    }

    private static double dist(final int[] path) {
        return IntStream.range(0, STOPS)
            .mapToDouble(i -> ADJACENCE[path[i]][path[(i + 1) % STOPS]])
            .sum();
    }

    public static void main(String[] args) {
        final Engine<EnumGene<Integer>, Double> engine = Engine.builder(TravelingSalesman::dist, codecs.ofPermutation(STOPS))
            .optimize(Optimize.MINIMUM)
            .maximalPhenotypeAge(11)
            .populationSize(500)
            .alterers(new SwapMutator<>(0.2), new PartiallyMatchedCrossover<>(0.35))
            .build();

        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        final Phenotype<EnumGene<Integer>, Double> best = engine.stream()
            .limit(bySteadyFitness(15))
            .limit(250)
            .peek(statistics)
            .collect(toBestPhenotype());

        System.out.println(statistics);
        System.out.println(best);
    }

}
