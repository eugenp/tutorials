package com.baeldung.hexagonal.adapters;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.hexagonal.entity.Student;
import com.baeldung.hexagonal.ports.StudentRepositoryPort;

public class InMemoryStudentRepositoryAdapter implements StudentRepositoryPort {

    private Map<Integer, Student> studentsMap = new HashMap<Integer, Student>();

    @Override
    public Student saveStudent(Student student) {
        studentsMap.put(student.getId() , student);
        return student;
    }

    @Override
    public Student getStudent(int id) {
        return studentsMap.get(id);
    }

}
