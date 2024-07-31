package com.baeldung.datafaker;

import net.datafaker.Faker;

import java.io.Serializable;
import java.util.List;

public class MixedCollection {
    public static final int MIN = 1;
    public static final int MAX = 20;
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println(getMixedCollection());
    }

    static List<? extends Serializable> getMixedCollection() {
        return faker.collection(
                    () -> faker.date().birthday(),
                    () -> faker.name().fullName()
                )
            .len(MIN, MAX)
            .generate();
    }
}
