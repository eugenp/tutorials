package com.baeldung.optional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrElseAndOrElseGet {

    public static List<String> names = Arrays.asList("John", "Jones", "Kelly", "Cristina", "Raven");

    public String getRandomName() {
        Random random = new Random();
        int index = random.nextInt(5);
        return names.get(index);
    }

    public String getNameUsingOrElse(String name) {
        return Optional.ofNullable(name)
            .orElse(getRandomName());
    }

    public String getNameUsingOrElseGet(String name) {
        return Optional.ofNullable(name)
            .orElseGet(() -> getRandomName());
    }
}
