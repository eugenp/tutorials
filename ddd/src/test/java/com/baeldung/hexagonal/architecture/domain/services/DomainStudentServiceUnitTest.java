package com.baeldung.hexagonal.architecture.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.architecture.domain.models.Student;
import com.baeldung.hexagonal.architecture.domain.repositories.StudentRepositoryPort;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

public class DomainStudentServiceUnitTest {
    
    private StudentRepositoryPort studentRepositoryPort;
    private DomainStudentService tested;
    
    @BeforeEach
    void setUp() {
        studentRepositoryPort = mock(StudentRepositoryPort.class);
        tested = new DomainStudentService(studentRepositoryPort);
    }

    @Test
    void shouldCreateStudent_thenSaveIt() {
        Student student = new Student();
        student.setId(1);
        student.setName("Lucas Williams");
        given(studentRepositoryPort.save(student)).willReturn(student);
        
        assertThat(tested.save(student)).isSameAs(student);
    }
    
    @Test
    void shouldFindStudentById_thenReturnIt() {
        Student student = new Student();
        student.setId(1);
        student.setName("Lucas Williams");
        when(studentRepositoryPort.findById(1)).thenReturn(Optional.of(student));
        
        assertThat(tested.findById(1)).isEqualTo(Optional.of(student));
    }

}

