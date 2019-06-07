package com.baeldung.hexagonal.architecture.controllers;

import com.baeldung.hexagonal.architecture.exception.BadRequestException;
import com.baeldung.hexagonal.architecture.exception.NotFoundException;
import com.baeldung.hexagonal.architecture.models.Student;

public interface StudentPort {

    void registerStudent(Student registerRequest) throws BadRequestException;

    Student viewRegistration(Integer registrationNumber) throws NotFoundException;
}
