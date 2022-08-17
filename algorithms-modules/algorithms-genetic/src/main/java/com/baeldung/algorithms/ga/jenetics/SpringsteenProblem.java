package com.baeldung.algorithms.ga.jenetics;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.jenetics.BitGene;
import org.jenetics.engine.Codec;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.Problem;
import org.jenetics.engine.codecs;
import org.jenetics.util.ISeq;

public class SpringsteenProblem implements Problem<ISeq<SpringsteenRecord>, BitGene, Double> {

    private ISeq<SpringsteenRecord> records;
    private double maxPricePerUniqueSong;

    public SpringsteenProblem(ISeq<SpringsteenRecord> records, double maxPricePerUniqueSong) {
        this.records = requireNonNull(records);
        this.maxPricePerUniqueSong = maxPricePerUniqueSong;
    }

    @Override
    public Function<ISeq<SpringsteenRecord>, Double> fitness() {
        return SpringsteenRecords -> {
            double cost = SpringsteenRecords.stream()
                .mapToDouble(r -> r.price)
                .sum();

            int uniqueSongCount = SpringsteenRecords.stream()
                .flatMap(r -> r.songs.stream())
                .collect(Collectors.toSet())
                .size();

            double pricePerUniqueSong = cost / uniqueSongCount;

            return pricePerUniqueSong <= maxPricePerUniqueSong ? uniqueSongCount : 0.0;
        };
    }

    @Override
    public Codec<ISeq<SpringsteenRecord>, BitGene> codec() {
        return codecs.ofSubSet(records);
    }

    public static void main(String[] args) {
        double maxPricePerUniqueSong = 2.5;

        SpringsteenProblem springsteen = new SpringsteenProblem(
            ISeq.of(new SpringsteenRecord("SpringsteenRecord1", 25, ISeq.of("Song1", "Song2", "Song3", "Song4", "Song5", "Song6")), new SpringsteenRecord("SpringsteenRecord2", 15, ISeq.of("Song2", "Song3", "Song4", "Song5", "Song6", "Song7")),
                new SpringsteenRecord("SpringsteenRecord3", 35, ISeq.of("Song5", "Song6", "Song7", "Song8", "Song9", "Song10")), new SpringsteenRecord("SpringsteenRecord4", 17, ISeq.of("Song9", "Song10", "Song12", "Song4", "Song13", "Song14")),
                new SpringsteenRecord("SpringsteenRecord5", 29, ISeq.of("Song1", "Song2", "Song13", "Song14", "Song15", "Song16")), new SpringsteenRecord("SpringsteenRecord6", 5, ISeq.of("Song18", "Song20", "Song30", "Song40"))),
            maxPricePerUniqueSong);

        Engine<BitGene, Double> engine = Engine.builder(springsteen)
            .build();

        ISeq<SpringsteenRecord> result = springsteen.codec()
            .decoder()
            .apply(engine.stream()
                .limit(10)
                .collect(EvolutionResult.toBestGenotype()));

        double cost = result.stream()
            .mapToDouble(r -> r.price)
            .sum();

        int uniqueSongCount = result.stream()
            .flatMap(r -> r.songs.stream())
            .collect(Collectors.toSet())
            .size();

        double pricePerUniqueSong = cost / uniqueSongCount;

        System.out.println("Overall cost:  " + cost);
        System.out.println("Unique songs:  " + uniqueSongCount);
        System.out.println("Cost per song: " + pricePerUniqueSong);
        System.out.println("Records:       " + result.map(r -> r.name)
            .toString(", "));

    }

}