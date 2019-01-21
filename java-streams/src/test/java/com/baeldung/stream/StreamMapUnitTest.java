package com.baeldung.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class StreamMapUnitTest {

    private Map<String, String> books;

    @Before
    public void setup() {
        books = new HashMap<>();
        books.put("978-0201633610", "Design patterns : elements of reusable object-oriented software");
        books.put("978-1617291999", "Java 8 in Action: Lambdas, Streams, and functional-style programming");
        books.put("978-0134685991", "Effective Java");
    }


    @Test
    public void whenOptionalVersionCalledForExistingTitle_thenReturnOptionalWithISBN() {
        Optional<String> optionalIsbn = books.entrySet().stream()
                .filter(e -> "Effective Java".equals(e.getValue()))
                .map(Map.Entry::getKey).findFirst();

        assertEquals("978-0134685991", optionalIsbn.get());
    }

    @Test
    public void whenOptionalVersionCalledForNonExistingTitle_thenReturnEmptyOptionalForISBN() {
        Optional<String> optionalIsbn = books.entrySet().stream()
                .filter(e -> "Non Existent Title".equals(e.getValue()))
                .map(Map.Entry::getKey).findFirst();

        assertEquals(false, optionalIsbn.isPresent());
    }

    @Test
    public void whenStringVersionCalledForExistingTitle_thenReturnISBN() {
        String isbn = books.entrySet().stream()
                .filter(e -> "Effective Java".equals(e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        assertEquals("978-0134685991", isbn);
    }

    @Test
    public void whenStringVersionCalledForNonExistingTitle_thenReturnNull() {
        String isbn = books.entrySet().stream()
                .filter(e -> "Non Existent Title".equals(e.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        assertEquals(null, isbn);
    }

}
