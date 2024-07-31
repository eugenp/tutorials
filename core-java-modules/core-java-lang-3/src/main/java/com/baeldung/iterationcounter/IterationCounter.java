package com.baeldung.iterationcounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class IterationCounter {
    public static final List<String> IMDB_TOP_MOVIES = Arrays.asList("The Shawshank Redemption",
      "The Godfather", "The Godfather II", "The Dark Knight");

    public static List<String> getRankingsWithForLoop(List<String> movies) {
        List<String> rankings = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String ranking = (i + 1) + ": " + movies.get(i);
            rankings.add(ranking);
        }
        return rankings;
    }

    public static List<String> getRankingsWithForEachLoop(List<String> movies) {
        List<String> rankings = new ArrayList<>();
        int i = 0;
        for (String movie : movies) {
            String ranking = (i + 1) + ": " + movies.get(i);
            rankings.add(ranking);

            i++;
        }
        return rankings;
    }

    public static List<String> getRankingsWithFunctionalForEachLoop(List<String> movies) {
        List<String> rankings = new ArrayList<>();
        forEachWithCounter(movies, (i, movie) -> {
            String ranking = (i + 1) + ": " + movie;
            rankings.add(ranking);
        });

        return rankings;
    }

    public static <T> void forEachWithCounter(Iterable<T> source, BiConsumer<Integer, T> consumer) {
        int i = 0;
        for (T item : source) {
            consumer.accept(i, item);
            i++;
        }
    }

    public static List<String> getRankingsWithStream(Stream<String> movies) {
        List<String> rankings = new ArrayList<>();
        movies.forEach(withCounter((i, movie) -> {
            String ranking = (i + 1) + ": " + movie;
            rankings.add(ranking);
        }));

        return rankings;
    }

    public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }
}
