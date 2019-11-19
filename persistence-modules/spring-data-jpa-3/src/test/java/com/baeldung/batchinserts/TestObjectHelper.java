package com.baeldung.batchinserts;

import com.baeldung.batchinserts.model.School;
import com.baeldung.batchinserts.model.Student;

public class TestObjectHelper {

    public static School createSchool(int nameIdentifier) {
        School school = new School();
        school.setName("School" + (nameIdentifier + 1));
        return school;
    }

    public static Student createStudent(School school) {
        Student student = new Student();
        student.setName("Student-" + school.getName());
        student.setSchool(school);
        return student;
    }
}
