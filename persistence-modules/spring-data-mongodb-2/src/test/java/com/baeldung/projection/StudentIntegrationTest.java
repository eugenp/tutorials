package com.baeldung.projection;

import com.baeldung.projection.config.ProjectionConfig;
import com.baeldung.projection.model.Student;
import com.baeldung.projection.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public void whenRetrievingAllStudents_thenReturnsCorrectNumberOfRecords() {
        // WHEN
        List<Student> result = studentRepository.findAll(0L, 5L);
        // THEN
        assertEquals(5, result.size());
    }

    @Test
    public void whenLimitingAndSkipping_thenReturnsLimitedStudents() {
        // WHEN
        List<Student> result = studentRepository.findAll(3L, 2L);
        // THEN
        assertEquals(2, result.size());
        assertEquals("Tina", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());
    }

    @Test
    public void whenFilteringByAge_thenReturnsStudentsMatchingCriteria() {
        // WHEN
        List<Student> result = studentRepository.findAllByAgeCriteria(30L, 0L, 10L);
        // THEN
        assertEquals(1, result.size());
        assertEquals("Usman", result.get(0).getName());
    }

    @Test
    public void whenFindingById_thenReturnsMatchingStudent() {
        // WHEN
        List<Student> result = studentRepository.findByStudentId("A", 0L, 5L);
        // THEN
        assertEquals(1, result.size());
        assertEquals("Abraham", result.get(0).getName());
    }

    @Test
    public void whenFindByStudentIdUsingPageable_thenReturnsPageOfStudents() {
        // GIVEN
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 5, sort);

        // WHEN
        Page<Student> resultPage = studentRepository.findAll(pageable);

        // THEN
        assertEquals(5, resultPage.getTotalElements());
        assertEquals("Maria", resultPage.getContent().get(0).getName());
    }
}