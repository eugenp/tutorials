package org.baeldung.spring.data.redis.repo;

import org.baeldung.spring.data.redis.config.RedisConfig;
import org.baeldung.spring.data.redis.model.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:/spring-config.xml" })
@ContextConfiguration(classes = RedisConfig.class)
public class StudentRepositoryTest {

    private StudentRepositoryImpl studentRepositoryImpl;

    @Before
    public void setUp(){
        studentRepositoryImpl = new StudentRepositoryImpl();
        RedisConfig redisConfig = new RedisConfig();
        studentRepositoryImpl.setRedisTemplate(redisConfig.redisTemplate());
    }

    @Test
    public void whenSavingStudent_thenAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepositoryImpl.saveStudent(student);
        final Student retrievedStudent = studentRepositoryImpl.findStudent(student.getId());
        assertEquals(student.getId(), retrievedStudent.getId());
    }

    @Test
    public void whenUpdatingStudent_thenAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepositoryImpl.saveStudent(student);
        student.setName("Richard Watson");
        studentRepositoryImpl.saveStudent(student);
        final Student retrievedStudent = studentRepositoryImpl.findStudent(student.getId());
        assertEquals(student.getName(), retrievedStudent.getName());
    }

    @Test
    public void whenSavingStudents_thenAllShouldAvailableOnRetrieval() throws Exception {
        final Student engStudent = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        final Student medStudent = new Student("Med2015001", "Gareth Houston", Student.Gender.MALE, 2);
        studentRepositoryImpl.saveStudent(engStudent);
        studentRepositoryImpl.saveStudent(medStudent);
        final Map<Object, Object> retrievedStudent = studentRepositoryImpl.findAllStudents();
        assertEquals(retrievedStudent.size(), 2);
    }

    @Test
    public void whenDeletingStudent_thenNotAvailableOnRetrieval() throws Exception {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepositoryImpl.saveStudent(student);
        studentRepositoryImpl.deleteStudent(student.getId());
        final Student retrievedStudent = studentRepositoryImpl.findStudent(student.getId());
        assertNull(retrievedStudent);
    }
}