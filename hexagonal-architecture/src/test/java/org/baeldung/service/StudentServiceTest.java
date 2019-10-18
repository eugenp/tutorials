package org.baeldung.service;

import org.assertj.core.api.Assertions;
import org.baeldung.persistence.entity.Student;
import org.baeldung.persistence.repository.StudentRepository;
import org.baeldung.service.ports.IStudentDb;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void createTest() {
        Student student = new Student();
        student.setId(Long.valueOf(1));
        student.setUserName("Baeldung");
        student.setFirstName("Baeldung");
        student.setLastName("Baeldung");

        Mockito.when(studentRepository.save(student)).thenReturn(student);
        assertThat(studentService.create(student)).isEqualTo(student);
    }

    @Test
    public void findStudentByIdTest() {
        Student student = new Student();
        student.setId(Long.valueOf(1));
        student.setUserName("Baeldung");
        student.setFirstName("Baeldung");
        student.setLastName("Baeldung");

        Mockito.when(studentRepository.findById((long) 1)).thenReturn(Optional.of((student)));
        assertThat(studentService.findStudentById((long) 1)).isEqualTo(Optional.of(student));
    }
}
