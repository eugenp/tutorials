package com.baeldung.hexagonal;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ConsoleRandomAdder adder = new ConsoleRandomAdder();
        adder.execute();
    }
}

class AdderConfig {
    RandomAdder production() {
        return new RandomAdder(new MathRandomProvider());
    }

    RandomAdder test() {
        return new RandomAdder(new TestRandomProvider());
    }
}

class ConsoleRandomAdder {
    RandomAdder randomAdder = new AdderConfig().production();

    void execute() {
        try {
            double input = readDouble();
            double result = randomAdder.addRandom(input);
            presentResult(result);
        } catch (Exception e) {
            System.out.println("This is not a valid number");
        }
    }

    Double readDouble() {
        System.out.println("Input a number");
        String s = System.console().readLine();
        return Double.parseDouble(s);
    }

    void presentResult(Double result) {
        System.out.println("Your number plus a random value:");
        System.out.println("" + result);
    }
}

class RandomAdder {
    RandomProvider randomProvider;

    RandomAdder(RandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    Double addRandom(Double number) {
        return number + randomProvider.random();
    }
}

interface RandomProvider {
    Double random();
}

class MathRandomProvider implements RandomProvider {
    public Double random() {
        return Math.random();
    }
}

class TestRandomProvider implements RandomProvider {
    Random generator = new Random(1);

    public Double random() {
        return generator.nextDouble();
    }
}
