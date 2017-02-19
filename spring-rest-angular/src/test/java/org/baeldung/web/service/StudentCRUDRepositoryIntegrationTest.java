package org.baeldung.web.service;

import static org.junit.Assert.*;

import org.baeldung.web.entity.Student;
import org.baeldung.web.main.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class StudentCRUDRepositoryIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    private static final String STUDENT_ENDPOINT = "http://localhost:8080/student/";
    private static int STUDENT_ID = 1;
    private static int STUDENT_AGE = 25;

    @Test
    public void whenStudentCRUDOperations_thenCorrect() {
        Student student = new Student(STUDENT_ID, "Bryan", "Male", 20);
        ResponseEntity<Student> postResponse = template.postForEntity(STUDENT_ENDPOINT, student, Student.class, "");
        assertEquals("status is not 201", HttpStatus.CREATED, postResponse.getStatusCode());

        student.setAge(25);
        Student patchResponse = template.patchForObject(STUDENT_ENDPOINT + "/" + STUDENT_ID, student, Student.class);
        assertEquals("age is not 25", Integer.valueOf(STUDENT_AGE), patchResponse.getAge());

        ResponseEntity<Student> getResponse = template.getForEntity(STUDENT_ENDPOINT + "/" + STUDENT_ID, Student.class, "");
        assertEquals("status is not 200", HttpStatus.OK, getResponse.getStatusCode());

        template.delete(STUDENT_ENDPOINT + "/" + STUDENT_ID);

        getResponse = template.getForEntity(STUDENT_ENDPOINT + "/" + STUDENT_ID, Student.class, "");
        assertEquals("status is not 404", HttpStatus.NOT_FOUND, getResponse.getStatusCode());

    }
}
