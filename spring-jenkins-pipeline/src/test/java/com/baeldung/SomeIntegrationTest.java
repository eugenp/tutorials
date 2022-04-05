package com.baeldung;

import com.baeldung.domain.Student;
import com.baeldung.repository.StudentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SpringJenkinsPipelineApplication.class})
public class SomeIntegrationTest {
    @Autowired
    private StudentRepository studentRepository;

    @Before
    public void setup() {
        Student student = new Student("Paul", "Smith", "64377473774", "me@mailprovider.com");
        studentRepository.save(student);
    }

    @Test
    public void whenInserting_andCount_thenWeDontGetZero() {
        long count = studentRepository.count();

        assertNotEquals(0, count);
    }

    @After
    public void clean() {
        studentRepository.deleteAll();
    }
}
