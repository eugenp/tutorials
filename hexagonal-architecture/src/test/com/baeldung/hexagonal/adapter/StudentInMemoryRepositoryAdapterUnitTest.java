package com.baeldung.hexagonal.adapter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.hexagonal.domain.Student;

public class StudentInMemoryRepositoryAdapterUnitTest {

    StudentInMemoryRepositoryAdapter repositoryAdapter;

    @Before
    public void init(){
        repositoryAdapter = new StudentInMemoryRepositoryAdapter();
    }

    @Test
    public void givenStudent_whenSaveIsCalled_thenStudentMustBeSaved(){
        Student student = new Student();
        student.setId("1");
        student.setName("Test");

        repositoryAdapter.saveStudent(student);

        assertEquals(student, repositoryAdapter.getStudent("1"));
    }

    @Test
    public void givenStudentId_whenDeleteIsCalled_thenStudentMustBeDeleted(){

        repositoryAdapter.deleteStudent("1");

        assertEquals(null, repositoryAdapter.getStudent("1"));
    }
}