package com.baeldung.list.listofhashmaps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class ListOfHashMapsUnitTest {

    @Test
    void givenMaps_whenAddToList_thenListContainsMaps() {
        List<HashMap<String, List<String>>> booksAuthorsMapsList = new ArrayList<>();
        HashMap<String, List<String>> javaBooksAuthorsMap = new HashMap<>();
        HashMap<String, List<String>> phpBooksAuthorsMap = new HashMap<>();

        javaBooksAuthorsMap.put("Head First Java", Arrays.asList("Kathy Sierra", "Bert Bates"));
        javaBooksAuthorsMap.put("Effective Java", Arrays.asList("Joshua Bloch"));
        javaBooksAuthorsMap.put("OCA Java SE 8", Arrays.asList("Kathy Sierra", "Bert Bates", "Elisabeth Robson"));
        phpBooksAuthorsMap.put("The Joy of PHP", Arrays.asList("Alan Forbes"));
        phpBooksAuthorsMap.put("Head First PHP & MySQL", Arrays.asList("Lynn Beighley", "Michael Morrison"));

        booksAuthorsMapsList.add(javaBooksAuthorsMap);
        booksAuthorsMapsList.add(phpBooksAuthorsMap);

        assertTrue(booksAuthorsMapsList.get(0)
            .keySet()
            .containsAll(new ArrayList<>(javaBooksAuthorsMap.keySet())));

        assertTrue(booksAuthorsMapsList.get(1)
            .keySet()
            .containsAll(new ArrayList<>(phpBooksAuthorsMap.keySet())));
    }
}