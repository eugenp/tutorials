package com.baeldung.hexagonal.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.baeldung.hexagonal.service.StudentService;

public class StudentInputAdapterUnitTest {

    StudentInputAdapter studentInput;
    StudentService studentService;

    @Before
    public void init(){
        studentService = Mockito.mock(StudentService.class);
        studentInput = new StudentInputAdapter(studentService);
    }

    @Test
    public void givenStudentName_whenRegisterStudentIsCalled_thenStudentIDMustBeReturned(){
        when(studentService.addStudent(Mockito.anyString())).thenReturn("testId");

        String name = "testName";

        assertEquals("testId", studentInput.registerStudent(name));
    }

    @Test
    public void givenStudentId_whenDeRegisterStudentIsCalled_thenStudentMustBeDeregistered(){
        when(studentService.removeStudent(Mockito.anyString())).thenReturn(true);

        assertTrue(studentInput.deregisterStudent(Mockito.anyString()));
    }
}