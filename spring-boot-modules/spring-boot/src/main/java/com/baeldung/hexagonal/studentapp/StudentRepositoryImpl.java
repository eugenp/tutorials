package com.baeldung.hexagonal.studentapp;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    
    private static Map<Long, Student> studentMap = new HashMap<>();


    @Override
    public Student addStudent(Student student) {
        return studentMap.put(student.getId(), student);
    }

    @Override
    public List<Student> getStudents() {
        return new ArrayList<>(studentMap.values());
    }
}
