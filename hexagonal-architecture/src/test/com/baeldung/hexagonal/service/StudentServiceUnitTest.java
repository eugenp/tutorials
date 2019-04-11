package com.baeldung.hexagonal.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.baeldung.hexagonal.adapter.StudentInMemoryRepositoryAdapter;
import com.baeldung.hexagonal.domain.Student;

public class StudentServiceUnitTest {

    StudentService studentService;
    StudentInMemoryRepositoryAdapter repositoryAdapter;

    @Before
    public void init(){
        repositoryAdapter = Mockito.mock(StudentInMemoryRepositoryAdapter.class);
        studentService = new StudentService(repositoryAdapter);
    }

    @Test
    public void givenStudentName_whenAddStudentIsCalled_thenStudentMustBeSaved(){
        String name = "testName";

        assertTrue(studentService.addStudent(name) != null);
    }

    @Test
    public void givenStudentId_whenDeRegisterStudentIsCalled_thenStudentMustBeDeregistered(){
        when(repositoryAdapter.getStudent(Mockito.anyString())).thenReturn(Mockito.mock(Student.class));

        assertTrue(studentService.removeStudent(Mockito.anyString()));
    }
}
