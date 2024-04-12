package com.baeldung.map.identity;

import com.baeldung.map.identity.IdentityHashMapDemonstrator.Book;
import org.junit.jupiter.api.Test;

import java.util.IdentityHashMap;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityHashMapDemonstratorUnitTest {

    @Test
    public void givenIdentityHashMap_whenNewObjectWithSameKey_thenAddsAsNewValue() {
        IdentityHashMap<String, String> identityHashMap = IdentityHashMapDemonstrator.createWithSimpleData();
        String newGenreKey = new String("genre");
        identityHashMap.put(newGenreKey, "Drama");

        assertEquals(5, identityHashMap.size());
        assertEquals("Fantasy", identityHashMap.get("genre"));
        assertEquals("Drama", identityHashMap.get(newGenreKey));
    }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_19)
    public void removeEntryComparingValueByEquality() {
        Book book = new Book("A Passage to India", 1924);
        IdentityHashMap<Book, String> identityHashMap = new IdentityHashMap<>(10);
        identityHashMap.put(book, "A great work of fiction");
        identityHashMap.remove(book, new String("A great work of fiction"));
        assertEquals(null, identityHashMap.get(book));
    }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_19)
    public void replaceEntryComparingValueByEquality() {
        Book book = new Book("A Passage to India", 1924);
        IdentityHashMap<Book, String> identityHashMap = new IdentityHashMap<>(10);
        identityHashMap.put(book, "A great work of fiction");
        identityHashMap.replace(book, new String("A great work of fiction"), "One of the greatest books");
        assertEquals("One of the greatest books", identityHashMap.get(book));
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_20)
    public void dontRemoveEntryComparingValueByEquality() {
        Book book = new Book("A Passage to India", 1924);
        IdentityHashMap<Book, String> identityHashMap = new IdentityHashMap<>(10);
        identityHashMap.put(book, "A great work of fiction");
        identityHashMap.remove(book, new String("A great work of fiction"));
        assertEquals("A great work of fiction", identityHashMap.get(book));
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_20)
    public void dontReplaceEntryComparingValueByEquality() {
        Book book = new Book("A Passage to India", 1924);
        IdentityHashMap<Book, String> identityHashMap = new IdentityHashMap<>(10);
        identityHashMap.put(book, "A great work of fiction");
        identityHashMap.replace(book, new String("A great work of fiction"), "One of the greatest books");
        assertEquals("A great work of fiction", identityHashMap.get(book));
    }
}
