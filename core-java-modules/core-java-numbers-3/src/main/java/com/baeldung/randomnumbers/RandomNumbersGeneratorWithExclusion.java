package com.baeldung.randomnumbers;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Random;

public class RandomNumbersGeneratorWithExclusion {

    public static int getRandomWithExclusionUsingMathRandom(int min, int max, int[] exclude) {
        Arrays.sort(exclude);
        int random = min + (int) ((max - min + 1 - exclude.length) * Math.random());
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    public static int getRandomNumberWithExclusionUsingNextInt(int min, int max, int[] exclude) {
        Random rnd = new Random();
        Arrays.sort(exclude);
        int random = min + rnd.nextInt(max - min + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    public int getRandomWithExclusion(int min, int max, int[] exclude) {
        Random rnd = new Random();
        OptionalInt random = rnd.ints(min, max + 1)
          .filter(num -> Arrays.stream(exclude).noneMatch(ex -> num == ex))
          .findFirst();
        return random.orElse(min);
    }

}
