package com.baeldung.hexagonalarch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.baeldung.hexagonalarch.domain.model.Student;
import com.baeldung.hexagonalarch.repository.StudentRepository;
import com.baeldung.hexagonalarch.service.StudentService;
import com.baeldung.hexagonalarch.service.impl.StudentServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class HexagonalArchApplicationTests {

  StudentService studentService;
  StudentRepository studentRepository;
  Student student;

  @BeforeEach
  public void setUp() {
    studentRepository = mock(StudentRepository.class);

    studentService = new StudentServiceImpl(studentRepository);
    student = new Student();
    student.setFirstName("Steve");
    student.setLastName("Jobs");
  }

  @Test
  public void addStudent_success() {
    when(studentRepository.save(any(Student.class))).thenReturn(student);

    Student testStudent = studentService.addStudent(student);
    assertEquals(student, testStudent);
  }

  @Test
  public void getStudent_success() {
    when(studentRepository.findByStudentId(1L)).thenReturn(Optional.of(student));

    Student testStudent = studentService.getStudent(1L);
    assertEquals(student, testStudent);
  }

  @Test
  public void getStudent_failure() {
    doThrow(new RuntimeException()).when(studentRepository).findByStudentId(-1L);
  }

  @Test
  public void getAllStudent_success() {
    List<Student> students = studentRepository.findAll();

    List<Student> testStudents = studentService.getAllStudents();
    assertNotNull(students);
    assertNotNull(testStudents);
    assertEquals(students, testStudents);
  }

}
