package org.baeldung.service;

import org.baeldung.adapter.secondary.StudentRepositoryAdapter;
import org.baeldung.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

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
    private StudentRepositoryAdapter studentRepositoryAdapter;

    @Test
    public void createTest() {
        Student student = new Student();
        student.setId(Long.valueOf(1));
        student.setFirstName("Baeldung");
        student.setAge(10);
        student.setSchoolFees(100.00);

        Mockito.when(studentRepositoryAdapter.save(student)).thenReturn(student);
        assertThat(studentService.create(student)).isEqualTo(student);
    }

    @Test
    public void findStudentByIdTest() {
        Student student = new Student();
        student.setId(Long.valueOf(1));
        student.setFirstName("Baeldung");
        student.setAge(10);
        student.setSchoolFees(100.00);

        Mockito.when(studentRepositoryAdapter.findById((long) 1)).thenReturn(Optional.of((student)));
        assertThat(studentService.findStudentById((long) 1)).isEqualTo(Optional.of(student));
    }
}
