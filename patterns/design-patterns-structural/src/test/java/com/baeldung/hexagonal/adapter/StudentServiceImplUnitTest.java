package com.baeldung.hexagonal.adapter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.baeldung.hexagonal.domain.Student;
import com.baeldung.hexagonal.domain.StudentServiceImpl;
import com.baeldung.hexagonal.port.StudentService;


@RunWith(JUnit4.class)
public class StudentServiceImplUnitTest {

    private StudentService service;

    @Before
    public void beforeTest() {
        service = new StudentServiceImpl();
    }

    @Test
    public void givenExistingStudentId_whenRetrievingStudentUsingExistingId_thenCorrespondingStudentIsReturned() {
        
    }

    @Test
    public void givenNewStudent_whenCreatingStudentRecord_thenNewStudentRecordShouldBeCreated() {
        // Given
        Student johnSmith = new Student("John", "Smith");
        
        // When
        Student createdStudent = service.create(johnSmith);
        
        // Then
        assertThat(johnSmith, is(equalTo(createdStudent)));
    }

    @Test
    public void givenExistingStudentWithCorrespondingId_whenUpdatingInformationForExistingStudent_thenCorrespondingStudentIsUpdated() {
        // Given
        Student existingTuring = service.read(1L);
        assertNotNull(existingTuring);
        
        Student turingFullName = new Student("Alan Mathison", "Turing");
        
        // When        
        Student turingUpdatedWithFullName = service.update(1L, turingFullName);
        
        // Then
        assertThat(turingFullName, is(equalTo(turingUpdatedWithFullName)));
    }

}
