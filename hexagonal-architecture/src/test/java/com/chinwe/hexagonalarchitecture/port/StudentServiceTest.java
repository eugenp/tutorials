package com.chinwe.hexagonalarchitecture.port;

import com.chinwe.hexagonalarchitecture.domain.model.Student;
import com.chinwe.hexagonalarchitecture.domain.responsestatus.Status;
import com.chinwe.hexagonalarchitecture.domain.service.StudentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentServiceTest {



    @TestConfiguration
    static class StudentServiceTestConfig {
        @Bean
        public StudentService studentService() {
            return new StudentServiceImpl();
        }
    }


    @MockBean
    private  StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @BeforeEach
     void setUp() {
        Student  studentOne = new Student("Tony", 1L, 100L);
        Student  studentTwo = new Student("Chinwe", 2L, 200L);
        Student  studentThree = new Student("Frank", 3L, 300L);
        Status statusOne =new Status(200,"Student added successfully");
        Status statusTwo =new Status(200,"Student was removed successfully");
        List<Student> students = Arrays.asList(studentOne, studentTwo, studentThree);

        Mockito.when(studentRepository.getStudents()).thenReturn(students);
        Mockito.when(studentRepository.getStudentById(studentOne.getStudentId())).thenReturn(studentOne);
        Mockito.when(studentRepository.getStudentById(4L)).thenReturn(null);
        Mockito.when(studentRepository.addStudent(studentTwo)).thenReturn(statusOne);
        Mockito.when(studentRepository.removeStudent(studentThree.getStudentId())).thenReturn(statusTwo);
    }


    @Test
    void givenThreeStudents_whenGetAllStudents_thenThreeStudentsReturned() {
        Student  studentOne = new Student("Tony", 1L, 100L);
        Student  studentTwo = new Student("Chinwe", 2L, 200L);
        Student  studentThree = new Student("Frank", 3L, 300L);
        List<Student> students = studentService.getStudents();
        assertThat(students).hasSize(3).extracting(Student::getStudentId).contains(studentOne.getStudentId(), studentTwo.getStudentId(), studentThree.getStudentId());
        verifyGetStudentsIsCalledOnce();

    }

    @Test
    void whenValidStudentId_thenStudentShouldBeFound() {
        Long id = 1L;
        Student student = studentService.getStudentById(1L);
        assertThat(student.getStudentId()).isEqualTo(id);
        verifyGetByStudentIdIsCalledOnce();
    }

    @Test
    void whenAddStudent_thenStatusReturned() {
        Student  studentOne = new Student("Tony", 4L, 400L);
        Status status=new Status(201,"Student added");
        Assertions.assertThat(studentService.addStudent(studentOne)).extracting(Status::getDescription).as(status.getDescription());
    }


    @Test
    void whenRemoveStudentById_thenStatusReturned() {
        Student  studentOne = new Student("Tony", 2L, 200L);
        Status status=new Status(200,"Student removed");
        assertThat(studentService.removeStudent(studentOne.getStudentId())).extracting(Status::getDescription).as(status.getDescription());
    }

    private void verifyGetStudentsIsCalledOnce() {
        Mockito.verify(studentRepository, VerificationModeFactory.times(1)).getStudents();
        Mockito.reset(studentRepository);
    }

    private void verifyGetByStudentIdIsCalledOnce() {
        Mockito.verify(studentRepository, VerificationModeFactory.times(1)).getStudentById(Mockito.anyLong());
        Mockito.reset(studentRepository);
    }
}