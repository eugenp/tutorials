package com.baeldung.shufflingcollections;

import java.util.*;

public class ShufflingCollections {
    private void shuffleList() {
        List<String> students = Arrays.asList("Foo", "Bar", "Baz", "Qux");

        Collections.shuffle(students);
    }

    private void shuffleMap() {
        Map<Integer, String> studentsById = new HashMap<>();
        studentsById.put(1, "Foo");
        studentsById.put(2, "Bar");
        studentsById.put(3, "Baz");
        studentsById.put(4, "Qux");

        List<Integer> shuffledStudentIds = new ArrayList<>(studentsById.keySet());
        Collections.shuffle(shuffledStudentIds);

        List<String> shuffledStudents = new ArrayList<>();
        for(int id: shuffledStudentIds) {
            shuffledStudents.add(studentsById.get(id));
        }
    }

    private void shuffleSet() {
        Set<String> students = new HashSet<>(Arrays.asList("Foo", "Bar", "Baz", "Qux"));

        List<String> studentList = new ArrayList<>(students);
        Collections.shuffle(studentList);
    }

    public static void main(String[] args) {

    }

    private void shuffleListsDeterministic() {
        List<String> students_1 = Arrays.asList("Foo", "Bar", "Baz", "Qux");
        List<String> students_2 = Arrays.asList("Foo", "Bar", "Baz", "Qux");

        int seedValue = 10;

        Collections.shuffle(students_1, new Random(seedValue));
        Collections.shuffle(students_2, new Random(seedValue));

        assert students_1.equals(students_2);
    }
}
