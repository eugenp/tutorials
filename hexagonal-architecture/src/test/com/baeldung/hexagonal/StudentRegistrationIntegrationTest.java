package com.baeldung.hexagonal;

import com.baeldung.hexagonal.service.StudentService;
import org.junit.Assert;
import org.junit.Test;

import com.baeldung.hexagonal.adapter.StudentInMemoryRepositoryAdapter;
import com.baeldung.hexagonal.adapter.StudentInputAdapter;

public class StudentRegistrationIntegrationTest {

    StudentInMemoryRepositoryAdapter repositoryAdapter = new StudentInMemoryRepositoryAdapter();

    StudentService studentService = new StudentService(repositoryAdapter);

    StudentInputAdapter inputAdapter = new StudentInputAdapter(studentService);

    @Test
    public void givenStudentNameIsEntered_whenStudentIsCreated_thenStudentWillBePresentInRepository(){
        String studentId = inputAdapter.registerStudent("Tim");

        Assert.assertTrue(repositoryAdapter.getStudent(studentId)!=null);
    }

    @Test
    public void givenStudentIdIsEnteredForRemoval_whenStudentIsRemoved_thenStudentWillBeAbsentInRepository(){
        String studentId = inputAdapter.registerStudent("Tom");

        boolean result = inputAdapter.deregisterStudent(studentId);

        Assert.assertTrue(result && repositoryAdapter.getStudent(studentId)==null);
    }

}