package com.baeldung.akkastreams;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AverageRepository {
    CompletionStage<Double> save(Double average) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("saving average: " + average);
            return average;
        });
    }
}
