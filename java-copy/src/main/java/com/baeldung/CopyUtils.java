package com.baeldung;

import java.util.stream.Collectors;

public class CopyUtils {

    public static Student shallowCopy(Student s1) {
        return new Student(s1.getFirstName(), s1.getLastName(), s1.getGrades());
    }

    public static Student deepCopy(Student s1) {
        return new Student(s1.getFirstName(), s1.getLastName(),
                // create deep copy of the grades list
                s1.getGrades().stream().map(g -> new Grade(g.getCourseCode(), g.getGrade())).collect(Collectors.toList()));
    }
}
