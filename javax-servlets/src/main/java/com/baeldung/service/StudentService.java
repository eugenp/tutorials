package com.baeldung.service;

import com.baeldung.model.Student;

/**
 * 
 * @author haseeb
 *
 */
public class StudentService {

    /**
     * 
     * @param id
     * @return
     */
    public Student getStudent(int id) {

        Student student = null;

        switch (id) {
        case 1:
            student = new Student(1, "John", "Doe");
            break;
        case 2:
            student = new Student(2, "Jane", "Goodall");
            break;
        case 3:
            student = new Student(3, "Max", "Born");
            break;
        }
        return student;
    }
}
