package com.baeldung.spring.jdbc.replacedeprecated;

import com.baeldung.spring.jdbc.replacedeprecated.model.Student;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Sql(value = "student.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "drop_student.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StudentDaoWithJdbcTemplateMethodsUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(StudentDaoWithJdbcTemplateMethodsUnitTest.class);
    @Autowired
    StudentDaoWithDeprecatedJdbcTemplateMethods studentDaoWithDeprecatedJdbcTemplateMethods;
    @Autowired
    StudentDaoWithPreferredJdbcTemplateMethods studentDaoWithPreferredJdbcTemplateMethods;

    @Test
    public void givenPreferredMethodQuery_whenArgsAgeAndGender_thenReturnStudents() {
        List<Student> students = studentDaoWithPreferredJdbcTemplateMethods.getStudentsOfAgeAndGender(4, "Female");

        for (Student student: students) {
            logger.info("Student Name: " + student.getStudentName() + " Student gender: " + student.getStudentGender());
        }
        assertEquals(6, students.size());
    }

    @Test
    public void givenPreferredMethodQuery_whenArgsAgeGenderAndGrade_thenReturnStudents() {
        List<Student> students = studentDaoWithPreferredJdbcTemplateMethods.getStudentsOfAgeGenderAndGrade(4, "Female", 2);

        for (Student student: students) {
            logger.info("Student Name: " + student.getStudentName() + " Student gender: " + student.getStudentGender()
            + " Student grade: " + student.getGrade());
        }
        assertEquals(5, students.size());
    }

    @Test
    public void givenDeprecatedMethodQuery_whenArgsAgeGenderAndGrade_thenReturnStudents() {
        List<Student> students = studentDaoWithDeprecatedJdbcTemplateMethods.getStudentsOfAgeGenderAndGrade(4, "Female", 2);

        for (Student student: students) {
            logger.info("Student Name: " + student.getStudentName() + " Student gender: " + student.getStudentGender()
                    + " Student grade: " + student.getGrade());
        }
        assertEquals(5, students.size());
    }

    @Test
    public void givenDeprecatedMethodQuery_whenArgsAgeAndGender_thenReturnStudents() {
        List<Student> students = studentDaoWithDeprecatedJdbcTemplateMethods.getStudentsOfAgeAndGender(4, "Female");
        for (Student student: students) {
            logger.info("Student Name: " + student.getStudentName() + " Student gender: " + student.getStudentGender());
        }
        assertEquals(6, students.size());
    }

    @Test
    public void givenDeprecatedMethodQuery_whenArgsGradeAndState_thenReturnStudents() {
        List<Student> students = studentDaoWithDeprecatedJdbcTemplateMethods.getStudentsOfGradeAndState(1, "New York");
        for (Student student: students) {
            logger.info("Student Name: " + student.getStudentName()
                    + " Student grade: " + student.getStudentGender()
                    + " Student State: " + student.getState());
        }
        assertEquals(6, students.size());
    }

    @Test
    public void givenPreferredMethodQuery_whenArgsGradeAndState_thenReturnStudents() {
        List<Student> students = studentDaoWithPreferredJdbcTemplateMethods.getStudentsOfGradeAndState(1, "New York");
        for (Student student: students) {
            logger.info("Student Name: " + student.getStudentName()
                    + " Student grade: " + student.getStudentGender()
                    + " Student State: " + student.getState());
        }
        assertEquals(6, students.size());
    }

    @Test
    public void givenDeprecatedMethodQuery_whenArgsGradeAndState_thenReturnCount() {
        Integer count = studentDaoWithDeprecatedJdbcTemplateMethods.getCountOfStudentsInAGradeFromAState(1, "New York");
        logger.info("Total students of grade 1 from New York: " + count);
        assertEquals(6, count);
    }
    @Test
    public void givenPreferredMethodQuery_whenArgsGradeAndState_thenReturnCount() {
        Integer count = studentDaoWithPreferredJdbcTemplateMethods.getCountOfStudentsInAGradeFromAState(1, "New York");
        logger.info("Total students of grade 1 from New York: " + count);
        assertEquals(6, count);
    }
    @Test
    public void givenDeprecatedMethodQueryForObject_whenArgsStudentIDAndGrade_thenReturnStudent() {
        Student student = studentDaoWithDeprecatedJdbcTemplateMethods.getStudentOfStudentIDAndGrade(4, 1);
        assertEquals(1, student.getGrade());
        assertEquals(4, student.getStudentId());
        logger.info("Student ID: " + student.getStudentId()
                + " Student Name: " + student.getStudentName() + " Student grade: " + student.getGrade());
    }

    @Test
    public void givenPreferredMethodQueryForObject_whenArgsStudentIDAndGrade_thenReturnStudent() {
        Student student = studentDaoWithPreferredJdbcTemplateMethods.getStudentOfStudentIDAndGrade(4, 1);
        assertEquals(1, student.getGrade());
        assertEquals(4, student.getStudentId());
        logger.info("Student ID: " + student.getStudentId()
                + " Student Name: " + student.getStudentName() + " Student grade: " + student.getGrade());
    }

    @Test
    public void givenPreferredMethodQueryForObject_whenArgsGenderAndGrade_thenReturnCount() {
        Integer count = studentDaoWithPreferredJdbcTemplateMethods.getCountOfGenderInAGrade("Female", 2);
        assertEquals(6, count);
        logger.info("Total number of Female Students: " + count);
    }

    @Test
    public void givenDeprecatedMethodQueryForObject_whenArgsGenderAndGrade_thenReturnCount() {
        Integer count = studentDaoWithPreferredJdbcTemplateMethods.getCountOfGenderInAGrade("Female", 2);
        assertEquals(6, count);
        logger.info("Total number of Female Students: " + count);
    }
}
