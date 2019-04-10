package com.baeldung.hexagonal;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.hexagonal.adapter.StudentInMemoryRepositoryAdapter;
import com.baeldung.hexagonal.adapter.StudentInputAdapter;
import com.baeldung.hexagonal.service.StudentService;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentRegistrationIntegrationTest {

    @Test
    public void givenStudentNameIsEntered_whenStudentIsCreated_thenStudentWillBePresentInRepository(){
        StudentInMemoryRepositoryAdapter repositoryAdapter = new StudentInMemoryRepositoryAdapter();
        StudentService studentService = new StudentService(repositoryAdapter);
        StudentInputAdapter inputAdapter = new StudentInputAdapter(studentService);

        String studentId = inputAdapter.registerStudent("Tim");

        assertTrue(repositoryAdapter.getStudent(studentId)!=null);
    }

    @Test
    public void givenStudentIdIsEnteredForRemoval_whenStudentIsValid_thenStudentWillBeRemovedFromRepository(){
        StudentInMemoryRepositoryAdapter repositoryAdapter = new StudentInMemoryRepositoryAdapter();
        StudentService studentService = new StudentService(repositoryAdapter);
        StudentInputAdapter inputAdapter = new StudentInputAdapter(studentService);

        String studentId = inputAdapter.registerStudent("Tom");

        boolean result = inputAdapter.deregisterStudent(studentId);

        assertTrue(result && repositoryAdapter.getStudent(studentId)==null);
    }

    @Test
    public void givenStudentIdIsEnteredForRemoval_whenStudentIsNotValid_thenStudentWillNotBeRemovedFromRepository(){
        StudentInMemoryRepositoryAdapter repositoryAdapter = new StudentInMemoryRepositoryAdapter();
        StudentService studentService = new StudentService(repositoryAdapter);
        StudentInputAdapter inputAdapter = new StudentInputAdapter(studentService);

        String studentId = inputAdapter.registerStudent("Tom");

        boolean result = inputAdapter.deregisterStudent("random");

        assertTrue(!result && repositoryAdapter.getStudent(studentId)!=null);
    }

}