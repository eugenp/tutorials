package com.baeldung.projection;

import com.baeldung.projection.config.ProjectionConfig;
import com.baeldung.projection.model.Student;
import com.baeldung.projection.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProjectionConfig.class)
public class StudentIntegrationTest {
    @Autowired
    private StudentRepository studentRepository;
    private List<Student> studentList;

    @Before
    public void setUp() {
        Student student1 = new Student("A", "Abraham", 15L);
        Student student2 = new Student("B", "Usman", 30L);
        Student student3 = new Student("C", "David", 20L);
        Student student4 = new Student("D", "Tina", 45L);
        Student student5 = new Student("E", "Maria", 33L);

        studentList = Arrays.asList(student1, student2, student3, student4, student5);
        studentRepository.saveAll(studentList);
    }

    @Test
    public void testFindAllWithPagination() {
        // WHEN
        List<Student> result = studentRepository.findAll(0L, 5L);
        // THEN
        assertEquals(5, result.size());

        // WHEN
        result = studentRepository.findAll(4L, 1L);
        // THEN
        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getName());
    }

    @Test
    public void testFindAllWithAgeCriteria() {
        // WHEN
        List<Student> result = studentRepository.findAllWithAgeCriteria(30L, 0L, 10L);
        // THEN
        assertEquals(1, result.size());
        assertEquals("Usman", result.get(0).getName());
    }

    @Test
    public void testFindByStudentId() {

        // WHEN
        List<Student> result = studentRepository.findByStudentId("A", 0L, 5L);
        // THEN
        assertEquals(1, result.size());
        assertEquals("Abraham", result.get(0).getName());
    }
}