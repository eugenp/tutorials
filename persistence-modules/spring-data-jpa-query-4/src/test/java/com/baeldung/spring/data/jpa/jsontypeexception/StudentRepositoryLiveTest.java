package com.baeldung.spring.data.jpa.jsontypeexception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@ActiveProfiles("test")
public class StudentRepositoryLiveTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentWithTypeAnnotationRepository studentWithJsonRepository;

    @Test
    void whenInsertStringToJsonColumn_thenThrowException() {
        Student student = new Student();
        student.setAdmitYear("2024");
        student.setAddress("{\"postCode\": \"TW9 2SF\", \"city\": \"London\"}");

        assertThrows(Exception.class, () -> studentRepository.save(student));
    }

    @Test
    void whenInsertStringToJsonColumnUsingQuery_thenThrowException() {
        assertThrows(Exception.class, () -> studentRepository.insertJsonData("2024","{\"postCode\": \"TW9 2SF\", \"city\": \"London\"}"));
    }

    @Test
    void whenInsertStringToJsonColumnWithAnnotation_thenReturnSuccess() throws JsonProcessingException {
        StudentWithTypeAnnotation student = new StudentWithTypeAnnotation();
        student.setAdmitYear("2024");
        student.setAddress("{\"postCode\": \"TW9 2SF\", \"city\": \"London\"}");
        studentWithJsonRepository.save(student);

        StudentWithTypeAnnotation retrievedStudent = studentWithJsonRepository.findById(student.getId()).orElse(null);

        assertThat(retrievedStudent).isNotNull();
        assertThat(retrievedStudent.getAddress()).isEqualTo("{\"postCode\": \"TW9 2SF\", \"city\": \"London\"}");
    }

    @Test
    void whenInsertStringToJsonColumnWithAnnotationUsingQuery_thenReturnSuccess() {
        StudentWithTypeAnnotation student = studentWithJsonRepository.insertJsonData("2024","{\"postCode\": \"TW9 2SF\", \"city\": \"London\"}");

        StudentWithTypeAnnotation retrievedStudent = studentWithJsonRepository.findById(student.getId()).orElse(null);

        assertThat(retrievedStudent).isNotNull();
        assertThat(retrievedStudent.getAddress()).isEqualTo("{\"city\": \"London\", \"postCode\": \"TW9 2SF\"}");
    }
}
