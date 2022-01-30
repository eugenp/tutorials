package com.baeldung.map.identity;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityHashMapDemonstrator {
    public static void main(String[] args) {
        IdentityHashMap<String, String> identityHashMap = createWithSimpleData();
        System.out.println("Map details: " + identityHashMap);
        IdentityHashMap<String, String> copiedMap = createFromAnotherMap(identityHashMap);

        updateWithNewValue(copiedMap);
        iterateIdentityHashMap(copiedMap);
        addNullKeyValue();
        demoHashMapVsIdentityMap();
        demoMutableKeys();

        Map<String, String> synchronizedMap = getSynchronizedMap();
        //Do multithreaded operations on synchronizedMap
    }

    private static void addNullKeyValue() {
        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();
        identityHashMap.put(null, "Null Key Accepted");
        identityHashMap.put("Null Value Accepted", null);
        assertEquals("Null Key Accepted", identityHashMap.get(null));
        assertEquals(null, identityHashMap.get("Null Value Accepted"));
    }

    private static void iterateIdentityHashMap(IdentityHashMap<String, String> identityHashMap) {
        // Iterating using entrySet
        System.out.println("Iterating values: ");
        Set<Map.Entry<String, String>> entries = identityHashMap.entrySet();
        for (Map.Entry<String, String> entry: entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Iterating using keySet
        System.out.println("Iterating values using keySet: ");
        for (String key: identityHashMap.keySet()) {
            System.out.println(key + ": " + identityHashMap.get(key));
        }

        // Throws error if we modify while iterating
        System.out.println("This iteration throws error: ");
        try {
            for (Map.Entry<String, String> entry: entries) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                identityHashMap.remove("title");
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println("This exception will raise for sure, if we modify while iterating");
        }
    }

    private static class Book {
        String title;
        int year;

        Book() {
            // nothing to do
        }

        Book(String title, int year) {
            this.title = title;
            this.year = year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return year == book.year && title.equals(book.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, year);
        }

        @Override
        public String toString() {
            return "Book{title='" + title + "', year=" + year + "}";
        }
    }

    private static void demoMutableKeys() {
        Book book1 = new Book("A Passage to India", 1924);
        Book book2 = new Book("Invisible Man", 1953);

        HashMap<Book, String> hashMap = new HashMap<>(10);
        hashMap.put(book1, "A great work of fiction");
        hashMap.put(book2, "won the US National Book Award");
        book2.year = 1952;
        assertEquals(null, hashMap.get(book2));
        System.out.println("HashMap: " + hashMap);

        IdentityHashMap<Book, String> identityHashMap = new IdentityHashMap<>(10);
        identityHashMap.put(book1, "A great work of fiction");
        identityHashMap.put(book2, "won the US National Book Award");
        book2.year = 1951;
        assertEquals("won the US National Book Award", identityHashMap.get(book2));
        System.out.println("IdentityHashMap: " + identityHashMap);
    }

    private static void demoHashMapVsIdentityMap() {
        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();
        identityHashMap.put("title", "Harry Potter and the Goblet of Fire");
        identityHashMap.put("author", "J. K. Rowling");
        identityHashMap.put("language", "English");
        identityHashMap.put("genre", "Fantasy");

        HashMap<String, String> hashMap = new HashMap<>(identityHashMap);
        hashMap.put(new String("genre"), "Drama");
        assertEquals(4, hashMap.size());
        System.out.println("HashMap content: " + hashMap);

        identityHashMap.put(new String("genre"), "Drama");
        assertEquals(5, identityHashMap.size());
        System.out.println("IdentityHashMap content: " + identityHashMap);
    }

    private static Map<String, String> getSynchronizedMap() {
        Map<String, String> synchronizedMap = Collections.synchronizedMap(new IdentityHashMap<String, String>());
        return synchronizedMap;
    }

    private static IdentityHashMap<String, String> createFromAnotherMap(Map<String, String> otherMap) {
        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>(otherMap);
        return identityHashMap;
    }

    private static void updateWithNewValue(IdentityHashMap<String, String> identityHashMap) {
        String oldTitle = identityHashMap.put("title", "Harry Potter and the Deathly Hallows");
        assertEquals("Harry Potter and the Goblet of Fire", oldTitle);
        assertEquals("Harry Potter and the Deathly Hallows", identityHashMap.get("title"));
    }

    public static void addValue(IdentityHashMap<String, String> identityHashMap, String key, String value) {
        identityHashMap.put(key, value);
    }

    public static void addAllValues(IdentityHashMap<String, String> identityHashMap, Map<String, String> otherMap) {
        identityHashMap.putAll(otherMap);
    }

    public static IdentityHashMap<String, String> createWithSimpleData() {
        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();
        identityHashMap.put("title", "Harry Potter and the Goblet of Fire");
        identityHashMap.put("author", "J. K. Rowling");
        identityHashMap.put("language", "English");
        identityHashMap.put("genre", "Fantasy");
        return identityHashMap;
    }
}
