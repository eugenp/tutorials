package com.baeldung.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

public class Java8ForEachTest {

    @Test
    public void compareForEachMethods_thenPrintResults() {

        List<String> names = new ArrayList<>();
        names.add("Larry");
        names.add("Steve");
        names.add("James");
        names.add("Conan");
        names.add("Ellen");

        // Java 5 - for-loop
        System.out.println("--- Enhanced for-loop ---");
        for (String name : names) {
            System.out.println(name);
        }

        // Java 8 - forEach
        System.out.println("--- forEach method ---");
        names.forEach(name -> System.out.println(name));

        // Anonymous inner class that implements Consumer interface
        System.out.println("--- Anonymous inner class ---");
        names.forEach(new Consumer<String>() {
            public void accept(String name) {
                System.out.println(name);
            }
        });

        // Create a Consumer implementation to then use in a forEach method
        Consumer<String> consumerNames = name -> {
            System.out.println(name);
        };
        System.out.println("--- Implementation of Consumer interface ---");
        names.forEach(consumerNames);

        // Print elements using a Method Reference
        System.out.println("--- Method Reference ---");
        names.forEach(System.out::println);

    }

}
