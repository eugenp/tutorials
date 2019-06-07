package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.exception.BadRequestException;
import com.baeldung.hexagonal.architecture.models.Entity.StudentModel;

public interface StudentRepositoryPort {

    void createStudentData(String name, String email, String batch, Integer id) throws BadRequestException;

    StudentModel getStudentData(Integer studentId);
}
