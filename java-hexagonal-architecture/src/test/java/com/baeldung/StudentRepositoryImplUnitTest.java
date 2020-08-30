package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.hexagonal.architecture.entity.Student;
import com.baeldung.hexagonal.architecture.repository.impl.StudentRepositoryImpl;

@ExtendWith(MockitoExtension.class)
class StudentRepositoryImplUnitTest {

    @InjectMocks
    private StudentRepositoryImpl studentRepository;

    @Test
    void shouldCreateAndGetStudent() {
        int studentId = 1;
        Student student = Student.builder()
            .id(studentId)
            .name("foo")
            .city("bar")
            .age(22)
            .build();
        studentRepository.createStudent(student);

        Optional<Student> actualStudent = studentRepository.getStudent(studentId);

        assertEquals(student, actualStudent.get());
    }

    @Test
    void testGetAllStudents() {
        Student studentOne = Student.builder()
            .id(1)
            .name("foo")
            .city("bar")
            .age(11)
            .build();
        Student studentTwo = Student.builder()
            .id(2)
            .name("foo")
            .city("bar")
            .age(22)
            .build();
        List<Student> students = new ArrayList<>();
        students.add(studentOne);
        students.add(studentTwo);
        studentRepository.createStudent(studentOne);
        studentRepository.createStudent(studentTwo);

        List<Student> actualStudents = studentRepository.getAllStudents();

        assertEquals(students, actualStudents);
    }

}
