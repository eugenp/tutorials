package com.baeldung.spring.data.redis.repo;

import com.baeldung.spring.data.redis.config.RedisConfig;
import com.baeldung.spring.data.redis.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void whenSavingStudent_thenAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        final Student retrievedStudent = studentRepository.findStudent(student.getId());
        assertEquals(student.getId(), retrievedStudent.getId());
    }

    @Test
    public void whenUpdatingStudent_thenAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        student.setName("Richard Watson");
        studentRepository.saveStudent(student);
        final Student retrievedStudent = studentRepository.findStudent(student.getId());
        assertEquals(student.getName(), retrievedStudent.getName());
    }

    @Test
    public void whenSavingStudents_thenAllShouldAvailableOnRetrieval() throws Exception {
        final Student engStudent = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        final Student medStudent = new Student("Med2015001", "Gareth Houston", Student.Gender.MALE, 2);
        studentRepository.saveStudent(engStudent);
        studentRepository.saveStudent(medStudent);
        final Map<Object, Object> retrievedStudent = studentRepository.findAllStudents();
        assertEquals(retrievedStudent.size(), 2);
    }

    @Test
    public void whenDeletingStudent_thenNotAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        studentRepository.deleteStudent(student.getId());
        final Student retrievedStudent = studentRepository.findStudent(student.getId());
        assertNull(retrievedStudent);
    }
}