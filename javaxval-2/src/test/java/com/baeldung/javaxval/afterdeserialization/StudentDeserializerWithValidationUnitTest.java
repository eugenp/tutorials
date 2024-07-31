package com.baeldung.javaxval.afterdeserialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import com.baeldung.javaxval.afterdeserialization.Student;
import com.baeldung.javaxval.afterdeserialization.StudentDeserializerWithValidation;

public class StudentDeserializerWithValidationUnitTest {

    private final String EXPECTED_ERROR_MESSAGE = "name: Student's name must be between 5 and 10 characters";
    private final String EXPECTED_STUDENT_NAME = "Daniel";
    private final String NAME_TOO_LONG_STUDENT_FILE = "nameTooLongStudent.json";
    private final String NAME_TOO_SHORT_STUDENT_FILE = "nameTooShortStudent.json";
    private final String SUBDIRECTORY = "afterdeserialization/";
    private final String VALID_STUDENT_FILE = "validStudent.json";

    @Test
    void givenValidStudent_WhenReadStudent_ThenReturnStudent() throws IOException {
        InputStream inputStream = getInputStream(VALID_STUDENT_FILE);
        Student result = StudentDeserializerWithValidation.readStudent(inputStream);
        assertEquals(EXPECTED_STUDENT_NAME, result.getName());
    }

    @Test
    void givenStudentWithTooShortName_WhenReadStudent_ThenThrows() {
        InputStream inputStream = getInputStream(NAME_TOO_SHORT_STUDENT_FILE);
        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> StudentDeserializerWithValidation.readStudent(inputStream));
        assertEquals(EXPECTED_ERROR_MESSAGE, constraintViolationException.getMessage());
    }

    @Test
    void givenStudentWithTooLongName_WhenReadStudent_ThenThrows() {
        InputStream inputStream = getInputStream(NAME_TOO_LONG_STUDENT_FILE);
        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> StudentDeserializerWithValidation.readStudent(inputStream));
        assertEquals(EXPECTED_ERROR_MESSAGE, constraintViolationException.getMessage());
    }

    private InputStream getInputStream(String fileName) {
        return getClass().getClassLoader()
            .getResourceAsStream(SUBDIRECTORY + fileName);
    }

}
