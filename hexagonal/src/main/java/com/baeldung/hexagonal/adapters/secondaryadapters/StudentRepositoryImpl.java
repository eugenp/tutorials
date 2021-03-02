package com.baeldung.hexagonal.adapters.secondaryadapters;

import com.baeldung.hexagonal.domain.Student;
import com.baeldung.hexagonal.outboundports.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class StudentRepositoryImpl implements StudentRepository {
    List<Student> students = new ArrayList<>();
    @Override
    public List<Student> getAllStudents() {
        return students;
    }

    @Override
    public Student getStudentById(int id) {
        return students.get(id);
    }

    @Override
    public void createStudent(Student student) {
        students.add(student);
    }

    @Override
    public boolean deleteStudent(Student student) {
        return true;
    }
}
