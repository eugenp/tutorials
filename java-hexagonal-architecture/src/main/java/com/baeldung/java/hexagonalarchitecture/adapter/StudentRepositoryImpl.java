package com.baeldung.java.hexagonalarchitecture.adapter;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.java.hexagonalarchitecture.domain.Student;
import com.baeldung.java.hexagonalarchitecture.port.StudentRepository;

public class StudentRepositoryImpl implements StudentRepository {

    private List<Student> studentList = new ArrayList<Student>();

    public void createStudent(Student student) {
        studentList.add(student);
        System.out.println("Created student: "+student);
    }

    public List<Student> listStudent() {
        return studentList;
    }

}
