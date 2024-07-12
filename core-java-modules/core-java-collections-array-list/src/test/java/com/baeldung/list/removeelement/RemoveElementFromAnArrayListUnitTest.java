package com.baeldung.list.removeelement;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveElementFromAnArrayListUnitTest {
    private List<String> getSportList() {
        List<String> sports = new ArrayList<>();
        sports.add("Football");
        sports.add("Basketball");
        sports.add("Baseball");
        sports.add("Boxing");
        sports.add("Cycling");
        return sports;
    }

    @Test
    void whenRemoveByIndex_thenCorrect() {
        List<String> sports = getSportList();
        assertEquals(5, sports.size());
        assertTrue(sports.contains("Basketball"));

        sports.remove(1); //removing Basketball
        assertEquals(4, sports.size());
        assertFalse(sports.contains("Basketball"));
    }

    @Test
    void whenRemoveLastByIndex_thenCorrect() {
        List<String> sports = getSportList();
        List<String> expected = List.of("Football", "Basketball", "Baseball", "Boxing");

        sports.remove(sports.size() - 1);
        assertEquals(expected, sports);
    }

    @Test
    void whenUsingJDK21RemoveLast_thenCorrect() {
        List<String> sports = getSportList();
        List<String> expected = List.of("Football", "Basketball", "Baseball", "Boxing");

        sports.removeLast();
        assertEquals(expected, sports);
    }


    @Test
    void whenRemoveByElement_thenCorrect() {
        List<String> sports = getSportList();
        assertEquals(5, sports.size());
        assertTrue(sports.contains("Basketball"));

        sports.remove("Basketball");
        assertEquals(4, sports.size());
        assertFalse(sports.contains("Basketball"));
    }

    @Test
    void whenRemovingWhileIterating_thenCorrect() {
        List<String> sports = getSportList();
        assertEquals(5, sports.size());
        assertTrue(sports.contains("Basketball"));

        Iterator<String> iterator = sports.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("Basketball")) {
                iterator.remove();
                break;
            }
        }
        assertEquals(4, sports.size());
        assertFalse(sports.contains("Basketball"));
    }

    @Test
    void whenRemoveIf_thenCorrect() {
        List<String> sports = getSportList();
        assertEquals(5, sports.size());
        assertTrue(sports.contains("Basketball"));

        sports.removeIf(p -> p.equals("Basketball"));

        assertEquals(4, sports.size());
        assertFalse(sports.contains("Basketball"));
    }
}