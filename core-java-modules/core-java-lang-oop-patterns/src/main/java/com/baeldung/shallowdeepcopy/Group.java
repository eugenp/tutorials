package com.baeldung.shallowdeepcopy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Group {

    List<Student> students;

    public Group(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public Group(Group original) {
        this.students = original.getStudents()
            .stream()
            .map((s) -> new Student(s))
            .collect(Collectors.toList());
    }

    public List<Student> getStudents() {
        return students;
    }
}
