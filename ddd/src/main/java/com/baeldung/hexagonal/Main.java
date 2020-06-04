package com.baeldung.hexagonal;

public class Main {
    public static void main(String[] args) {
        ConsoleRandomAdder adder = new ConsoleRandomAdder();
        adder.execute();
    }
}

// Convenience class where we inject the secondary adapters
class AdderConfig {
    RandomAdder production() {
        return new RandomAdder(new MathRandomProvider());
    }

    RandomAdder test() {
        return new RandomAdder(new TestRandomProvider());
    }
}

// Primary Adapter
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

// Primary Port (Domain Business Logic API)
class RandomAdder {
    RandomProvider randomProvider;// Domain is only aware of ports

    RandomAdder(RandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    Double addRandom(Double number) {// Adding Business Logic
        return number + randomProvider.random();
    }
}

// Secondary Port
interface RandomProvider {
    Double random();
}

// Secondary Adapter for production
class MathRandomProvider implements RandomProvider {
    public Double random() {
        return Math.random();
    }
}

// Secondary Adapter for tests
class TestRandomProvider implements RandomProvider {
    public Double random() {
        return 0.5;
    }
}
