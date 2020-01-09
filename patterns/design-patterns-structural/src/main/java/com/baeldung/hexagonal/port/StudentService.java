package com.baeldung.hexagonal.port;

import java.util.List;

import com.baeldung.hexagonal.domain.Student;


public interface StudentService {

    List<Student> readAll();
    
    Student read(Long id);
    
    Student create(Student student);
    
    Student update(Long id, Student student);
    
    void delete(Long id);
}
