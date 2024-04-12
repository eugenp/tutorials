package com.baeldung.randomgenerators;

import java.util.Comparator;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class GeneratorFactory {

    public static void main(String[] args) {
        System.out.println("Group\tName\tJumpable?\tSplittable?");
        RandomGeneratorFactory.all()
          .sorted(Comparator.comparing(RandomGeneratorFactory::name))
          .forEach(factory -> System.out.println(String.format("%s\t%s\t%s\t%s",
            factory.group(),
            factory.name(),
            factory.isJumpable(),
            factory.isSplittable())));
    }

    public static int getRandomInt(RandomGenerator generator, int bound) {
        return generator.nextInt(bound);
    }

    public static RandomGenerator getDefaultGenerator() {
        return RandomGeneratorFactory.getDefault().create();
    }

    public static RandomGenerator getJumpableGenerator() {
        return RandomGeneratorFactory.all()
          .filter(RandomGeneratorFactory::isJumpable)
          .findAny()
          .map(RandomGeneratorFactory::create)
          .orElseThrow(() -> new RuntimeException("Error creating a generator"));
    }

}
