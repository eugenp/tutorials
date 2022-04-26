package com.baeldung.randomgenerators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class SplittableGeneratorMultiThread {

    public static List<Integer> generateNumbersInMultipleThreads() {
        List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newCachedThreadPool();

        RandomGenerator.SplittableGenerator sourceGenerator = RandomGeneratorFactory
          .<RandomGenerator.SplittableGenerator>of("L128X256MixRandom")
          .create();

        sourceGenerator.splits(20).forEach((splitGenerator) -> {
            executorService.submit(() -> {
                numbers.add(splitGenerator.nextInt(10));
            });
        });

        return numbers;
    }

}
