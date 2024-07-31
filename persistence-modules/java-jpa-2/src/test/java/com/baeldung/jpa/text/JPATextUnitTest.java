package com.baeldung.jpa.text;

import static org.junit.Assert.assertEquals;

import jakarta.persistence.PersistenceException;

import org.junit.BeforeClass;
import org.junit.Test;

public class JPATextUnitTest {

    private static ExamRepository examRepository = null;

    @BeforeClass
    public static void once() {
        examRepository = new ExamRepository();
    }

    @Test
    public void givenExam_whenSaveExam_thenReturnExpectedExam() {
        Exam exam = new Exam();
        exam.setDescription("This is a description. Sometimes the description can be very very long! ");
        exam.setText("This is a text. Sometimes the text can be very very long!");
        exam.setShortText("A short text");

        exam = examRepository.save(exam);

        assertEquals(examRepository.find(exam.getId()), exam);
    }

    @Test(expected = PersistenceException.class)
    public void givenExamWithVeryLongShortText_whenSaveExam_thenThrowPersistenceException() {
        Exam exam = new Exam();
        exam.setDescription("This is a very long text");
        exam.setText("This is a long text");
        exam.setShortText("This is a very long long short text. Maybe this can cause problems!!");

        examRepository.save(exam);
    }

}