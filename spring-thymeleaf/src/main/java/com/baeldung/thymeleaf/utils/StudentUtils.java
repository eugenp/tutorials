package com.baeldung.thymeleaf.utils;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.thymeleaf.model.Student;

public class StudentUtils {

    private static List<Student> students = new ArrayList<Student>();

    public static List<Student> buildStudents() {
        if (students.isEmpty()) {
            Student student1 = new Student();
            student1.setId(1001);
            student1.setName("John Smith");
            student1.setGender('M');
            student1.setPercentage(Float.valueOf("80.45"));

            students.add(student1);

            Student student2 = new Student();
            student2.setId(1002);
            student2.setName("Jane Williams");
            student2.setGender('F');
            student2.setPercentage(Float.valueOf("60.25"));

            students.add(student2);
        }

        return students;
    }

}
