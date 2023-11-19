package com.baeldung.generics;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AnimalDemo {

    private static final Logger logger = LoggerFactory.getLogger(AnimalDemo.class);

    public static void main(String[] args) {
        List<Cat> cats = new ArrayList<>();
        cats.add(new Cat("Persian", "Bono"));
        cats.add(new Cat("Egyptian", "Lulu"));
        cats.add(new Cat("Siamese", "Ra"));

        order(cats);
        logger.info("Ordered cats: {}", cats);
    }

    public static <T extends Animal & Comparable<T>> void order(List<T> list) {
        list.sort(Comparable::compareTo);
    }
}
