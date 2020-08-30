package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.hexagonal.architecture.entity.Student;
import com.baeldung.hexagonal.architecture.repository.StudentRepository;
import com.baeldung.hexagonal.architecture.service.impl.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplUnitTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void shouldCreateStudent() {
        Student student = Student.builder()
            .id(123)
            .name("foo")
            .city("bar")
            .age(12)
            .build();

        studentService.createStudent(student);

        verify(studentRepository).createStudent(student);
    }

    @Test
    void shouldGetStudent() {
        int studentId = 123;
        Student student = Student.builder()
            .id(studentId)
            .name("foo")
            .city("bar")
            .age(12)
            .build();
        when(studentRepository.getStudent(studentId)).thenReturn(Optional.of(student));

        Optional<Student> actualStudent = studentService.getStudent(studentId);

        assertEquals(student, actualStudent.get());
    }
    
    @Test
    void shouldReturnEmptyWhenNoMatchingStudent() {
        int studentId = 123;
        when(studentRepository.getStudent(studentId)).thenReturn(Optional.empty());

        Optional<Student> actualStudent = studentService.getStudent(studentId);

        assertEquals(Optional.empty(), actualStudent);
    }

    @Test
    void shouldGetAllStudents() {
        Student studentOne = Student.builder()
            .id(123)
            .name("foo")
            .city("bar")
            .age(12)
            .build();
        Student studentTwo = Student.builder()
            .id(234)
            .name("bar")
            .city("foo")
            .age(22)
            .build();
        List<Student> students = new ArrayList<>();
        students.add(studentOne);
        students.add(studentTwo);
        
        when(studentRepository.getAllStudents()).thenReturn(students);

        List<Student> actualStudents = studentService.getAllStudents();

        assertEquals(students, actualStudents);
    }

}
