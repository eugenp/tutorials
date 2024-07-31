package com.baeldung.colonexamples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Examples of the different ways Java uses the colon (:) character.
 */
public class ColonExamples {

    private final static Logger LOG = LoggerFactory.getLogger(ColonExamples.class);

    public void example1_enhancedForLoop() {

        // Original style
        for(int i = 0; i < 10; i++) {
            // do something
        }

        // Using enhanced for loop
        int[] numbers = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for(int i : numbers) {
            // do something
        }

        // Using List instead of array
        List<Integer> numbersList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        for(Integer i : numbersList) {
            // do something
        }
    }

    public void example2_switchStatement(String animal) {

        // Original style
        if(animal.equals("cat")) {
            System.out.println("meow");
        }
        else if(animal.equals("lion")) {
            System.out.println("roar");
        }
        else if(animal.equals("dog") || animal.equals("seal")) {
            System.out.println("bark");
        }
        else {
            System.out.println("unknown");
        }

        // Using switch statement
        switch(animal) {
            case "cat":
                System.out.println("meow");
                break;
            case "lion":
                System.out.println("roar");
                break;
            case "dog":
            case "seal":
                System.out.println("bark");
                break;
            default:
                System.out.println("unknown");
        }
    }

    public void example3_labels() {

        // For loops without labels
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (checkSomeCondition()) {
                    break;
                }
            }
        }

        outterLoop: for(int i = 0; i < 10; i++) {
            innerLoop: for (int j = 0; j < 10; j++) {
                if (checkSomeCondition()) {
                    break outterLoop;
                }
            }
        }
    }

    public void example4_ternaryOperator() {

        // Original way using if/else
        int x;
        if(checkSomeCondition()) {
            x = 1;
        }
        else {
            x = 2;
        }

        // Using ternary operator
        x = checkSomeCondition() ? 1 : 2;

        // Using with other statements
        boolean remoteCallResult = callRemoteApi();
        LOG.info(String.format(
                "The result of the remote API call %s successful",
                        remoteCallResult ? "was" : "was not"
        ));
    }

    public void example5_methodReferences() {
        // Original way without lambdas and method references
        List<String> names = Arrays.asList("ross", "joey", "chandler");
        List<String> upperCaseNames = new ArrayList<>();
        for(String name : names) {
            upperCaseNames.add(name.toUpperCase());
        }

        // Using method reference with stream map operation
        List<String> petNames = Arrays.asList("ross", "joey", "chandler");
        List<String> petUpperCaseNames = petNames
            .stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        // Method reference with stream filter
        List<Animal> pets = Arrays.asList(new Cat(), new Dog(), new Parrot());
        List<Animal> onlyDogs = pets
            .stream()
            .filter(Dog.class::isInstance)
            .collect(Collectors.toList());

        // Method reference with constructors
        Set<Animal> onlyDogsSet = pets
            .stream()
            .filter(Dog.class::isInstance)
            .collect(Collectors.toCollection(TreeSet::new));
    }

    public void example6_asserttion() {
        // Original way without assertions
        Connection conn = getConnection();
        if(conn == null) {
            throw new RuntimeException("Connection is null");
        }

        // Using assert keyword
        assert getConnection() != null : "Connection is null";
    }

    private boolean checkSomeCondition() {
        return new Random().nextBoolean();
    }

    private boolean callRemoteApi() {
        return new Random().nextBoolean();
    }

    private Connection getConnection() {
        return null;
    }

    private static interface Animal {

    }
    private static class Dog implements Animal {

    }

    private static class Cat implements Animal {

    }

    private static class Parrot implements Animal {

    }
}
