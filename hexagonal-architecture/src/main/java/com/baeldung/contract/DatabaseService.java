package com.baeldung.contract;


import com.baeldung.model.Student;

public interface DatabaseService {

    void save(Student student);

    void update(Student student);

    void findById(int id);
}
