package com.baeldung.hexarch;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class StudentDataRepository implements IStudentDataRepository {

    protected Map<Integer, Student> studentsById;

    @Override
    public Student findById(int id) {
        return studentsById.get(id);
    }

    @Override
    public int add(Student student) {
        studentsById.put(student.getId(), student);
        return student.getId();
    }

}
