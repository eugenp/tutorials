package com.baeldung.hexarch;

public interface IStudentDataRepository {

    Student findById(int id);

    int add(Student student);

}
