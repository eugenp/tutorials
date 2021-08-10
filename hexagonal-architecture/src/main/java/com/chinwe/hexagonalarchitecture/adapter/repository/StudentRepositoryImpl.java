package com.chinwe.hexagonalarchitecture.adapter.repository;

import com.chinwe.hexagonalarchitecture.domain.model.Student;
import com.chinwe.hexagonalarchitecture.domain.responsestatus.Status;
import com.chinwe.hexagonalarchitecture.port.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static final Map<Long, Student> studentRepoMap = new HashMap<Long, Student>(0);

    @Override
    public List<Student> getStudents() {
        return new ArrayList<Student>(studentRepoMap.values());
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepoMap.get(studentId);
    }

    @Override
    public Status addStudent(Student student) {
        studentRepoMap.put(student.getStudentId(), student);
        return new Status(201,"Student added");
    }

    @Override
    public Status removeStudent(Long studentId) {
        if(studentRepoMap.get(studentId)!= null){
            studentRepoMap.remove(studentId);
            return new Status(200,"Student Successfully Removed");
        } else
            return new Status(404,"Student Is Not In The Database");
        }
}
