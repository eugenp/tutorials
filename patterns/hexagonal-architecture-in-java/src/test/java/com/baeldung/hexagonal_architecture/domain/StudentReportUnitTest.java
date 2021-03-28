package com.baeldung.hexagonal_architecture.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal_architecture.utils.ResultStatus;
import com.baeldung.hexagonal_architecture.utils.Subject;

class StudentReportUnitTest {

    @Test
    void testStudentReportPublisher() {

        Student student = new Student(123456L, "Eugen", "eugen@baeldung.com");

        StudentReport studentReport = new StudentReportBuilder()
            .setStudent(student)
            .setReportID(111L)
            .setSubjects(Arrays.asList(Subject.COMPUTER_SCIENCE, Subject.JAVA_PROGRAMMING))
            .setReportResultStatus(ResultStatus.PASS)
            .build();

        studentReport.publishStudentReport();

    }

}
