package com.baeldung.shufflingcollections;

import org.junit.Test;

import java.util.*;
import static org.junit.Assert.assertEquals;

public class ShufflingCollectionsUnitTest {

    @Test
    public void whenShufflingList_thenListIsShuffled() {
        List<String> students = Arrays.asList("Foo", "Bar", "Baz", "Qux");

        System.out.println("List before shuffling:");
        System.out.println(students);

        Collections.shuffle(students);
        System.out.println("List after shuffling:");
        System.out.println(students);
    }

    @Test
    public void whenShufflingMapKeys_thenValuesAreShuffled() {
        Map<Integer, String> studentsById = new HashMap<>();
        studentsById.put(1, "Foo");
        studentsById.put(2, "Bar");
        studentsById.put(3, "Baz");
        studentsById.put(4, "Qux");

        System.out.println("Students before shuffling:");
        System.out.println(studentsById.values());

        List<Integer> shuffledStudentIds = new ArrayList<>(studentsById.keySet());
        Collections.shuffle(shuffledStudentIds);

        System.out.println("Students after shuffling:");
        for(int id: shuffledStudentIds) {
            System.out.println(studentsById.get(id));
        }
    }

    @Test
    public void whenShufflingSet_thenElementsAreShuffled() {
        Set<String> students = new HashSet<>(Arrays.asList("Foo", "Bar", "Baz", "Qux"));

        System.out.println("Set before shuffling:");
        System.out.println(students);

        List<String> studentList = new ArrayList<>(students);

        Collections.shuffle(studentList);
        System.out.println("Shuffled set elements:");
        System.out.println(studentList);
    }

    @Test
    public void whenShufflingWithSameRandomness_thenElementsAreShuffledInSameOrder() {
        List<String> students_1 = Arrays.asList("Foo", "Bar", "Baz", "Qux");
        List<String> students_2 = Arrays.asList("Foo", "Bar", "Baz", "Qux");

        Collections.shuffle(students_1, new Random(5));
        Collections.shuffle(students_2, new Random(5));

        assertEquals(students_1, students_2);
    }
}
