package com.baeldung.hexagonaljava.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonaljava.entity.Student;

@Repository
public class StudentResultRepoImpl implements StudentResultRepo {

    private Map<Integer, Student> studentsMap = new HashMap<Integer, Student>();

    @Override
    public void save(Student student) {
        studentsMap.put(student.getId(), student);
    }

    @Override
    public Student getStudent(Integer id) {
        return studentsMap.get(id);
    }
}
