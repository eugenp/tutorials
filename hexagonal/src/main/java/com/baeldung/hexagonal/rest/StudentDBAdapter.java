package com.baeldung.hexagonal.rest;

import com.baeldung.hexagonal.beans.Student;
import com.baeldung.hexagonal.interfaces.StudentDBPort;

public class StudentDBAdapter implements StudentDBPort {

    @Override
    public Student createStudent(Student student) {
        // Implementation here
        return null;
    }

    @Override
    public Student updateStudent(Student student) {
        // Implementation here
        return null;
    }

    @Override
    public Student findById(long id) {
        // Implementation here
        return null;
    }
}
