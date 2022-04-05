package com.baeldung.boot.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.Application;
import com.baeldung.boot.domain.Student;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
@DirtiesContext
public class ExtendedStudentRepositoryIntegrationTest {
    @Resource
    private ExtendedStudentRepository extendedStudentRepository;

    @Before
    public void setup() {
        Student student = new Student(1, "john");
        extendedStudentRepository.save(student);
        Student student2 = new Student(2, "johnson");
        extendedStudentRepository.save(student2);
        Student student3 = new Student(3, "tom");
        extendedStudentRepository.save(student3);
    }

    @Test
    public void givenStudents_whenFindByName_thenGetOk() {
        List<Student> students = extendedStudentRepository.findByAttributeContainsText("name", "john");
        assertThat(students.size()).isEqualTo(2);
    }
}
