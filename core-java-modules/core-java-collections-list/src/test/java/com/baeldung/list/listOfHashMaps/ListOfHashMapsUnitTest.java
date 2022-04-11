package test.java.com.baeldung.list.listOfHashMaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class ListOfHashMapsUnitTest {
    List<HashMap<String, List<String>>> booksAuthorsMapsList = new ArrayList<>();

    @Test
    public void givenMaps_whenAddToList_thenListContainsMaps() {
        HashMap<String, List<String>> javaBooksAuthorsMap = new HashMap<>();
        HashMap<String, List<String>> phpBooksAuthorsMap = new HashMap<>();
        javaBooksAuthorsMap.put("Head First Java", Arrays.asList("Kathy Sierra", "Bert Bates"));
        javaBooksAuthorsMap.put("Effective Java", Arrays.asList("Joshua Bloch"));
        javaBooksAuthorsMap.put("OCA Java SE 8",
                Arrays.asList("Kathy Sierra", "Bert Bates", "Elisabeth Robson"));
        phpBooksAuthorsMap.put("The Joy of PHP", Arrays.asList("Alan Forbes"));
        phpBooksAuthorsMap.put("Head First PHP &amp; MySQL",
                Arrays.asList("Lynn Beighley", "Michael Morrison"));

        booksAuthorsMapsList.add(javaBooksAuthorsMap);
        booksAuthorsMapsList.add(phpBooksAuthorsMap);

        assertTrue(booksAuthorsMapsList.get(0).keySet().containsAll
                (javaBooksAuthorsMap.keySet().stream().collect(Collectors.toList())));
        assertTrue(booksAuthorsMapsList.get(1).keySet().containsAll
                (phpBooksAuthorsMap.keySet().stream().collect(Collectors.toList())));
    }
}
