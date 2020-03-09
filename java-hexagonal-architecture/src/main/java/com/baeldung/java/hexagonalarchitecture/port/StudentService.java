package com.baeldung.java.hexagonalarchitecture.port;

import java.util.List;

import com.baeldung.java.hexagonalarchitecture.domain.Student;

public interface StudentService {
    
    public void createStudentService(Student student);
    
    public List<Student> listStudentService();

}
