package com.baeldung;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.model.Student;
import com.baeldung.port.StudentDBPort;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.baeldung.*")
public class StudentServiceUnitTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    @Lazy
    private StudentDBPort studentService;

    @Test
    @Transactional
    public void createOneStudent_ThenVerifyIt() {
        Student student = new Student();
        student.setStudentId(2);
        student.setName("Simon");
        student.setAge(23);
        studentService.save(student);
        List<Student> allStudent = studentService.findAll();
        long count = allStudent.stream()
            .filter(e -> e.getStudentId() == student.getStudentId())
            .count();
        assertEquals(count, 1);
    }

}
