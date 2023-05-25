package com.baeldung.datafaker;

import java.util.List;
import net.datafaker.Faker;

public class Collection {
    public static final int MIN = 1;
    public static final int MAX = 100;
    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        System.out.println(getFictionalCharacters());
    }

    static List<String> getFictionalCharacters() {
        return faker.collection(
                        () -> faker.starWars().character(),
                        () -> faker.starTrek().character())
                .len(MIN, MAX)
                .generate();
    }
}
