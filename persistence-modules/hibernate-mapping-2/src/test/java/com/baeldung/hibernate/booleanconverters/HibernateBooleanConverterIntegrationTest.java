package com.baeldung.hibernate.booleanconverters;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.booleanconverters.model.Question;

public class HibernateBooleanConverterIntegrationTest {

    private static final String PROPERTY_FILE_NAME = "booleanconverters.cfg.xml";

    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    static void createSessionFactory() {
        sessionFactory = HibernateUtil.getSessionFactory(PROPERTY_FILE_NAME);
    }

    @BeforeEach
    void openSessionAndBeginTransaction() {
        session = sessionFactory.openSession();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    @Test
    void whenFieldAnnotatedWithYesNoConverter_ThenConversionWorks() {
        session.beginTransaction();
        UUID likeJavaQuestionId = UUID.randomUUID();
        UUID sydneyCapitalOfAustraliaQuestionId = UUID.randomUUID();
        session.persist(new QuestionBuilder().id(likeJavaQuestionId)
          .content("Do you like Java?")
          .correctAnswer(true)
          .build());
        session.persist(new QuestionBuilder().id(sydneyCapitalOfAustraliaQuestionId)
          .content("Is Sydney the capital of Australia?")
          .correctAnswer(false)
          .build());
        session.flush();

        char likeJavaQuestionCorrectAnswerDbValue = session.createNativeQuery(format("SELECT correctAnswer FROM Question WHERE id='%s'", likeJavaQuestionId), Character.class)
          .getSingleResult();
        char sydneyCapitalOfAustraliaQuestionCorrectAnswerDbValue = session.createNativeQuery(format("SELECT correctAnswer FROM Question WHERE id='%s'", sydneyCapitalOfAustraliaQuestionId), Character.class)
          .getSingleResult();
        session.close();

        assertEquals('Y', likeJavaQuestionCorrectAnswerDbValue);
        assertEquals('N', sydneyCapitalOfAustraliaQuestionCorrectAnswerDbValue);
    }

    @Test
    void whenFieldAnnotatedWithTrueFalseConverter_ThenConversionWorks() {
        session.beginTransaction();
        UUID codeTestedQuestionId = UUID.randomUUID();
        UUID earningsQuestionId = UUID.randomUUID();
        session.persist(new QuestionBuilder().id(codeTestedQuestionId)
          .content("Is this code tested?")
          .shouldBeAsked(true)
          .build());
        session.persist(new QuestionBuilder().id(earningsQuestionId)
          .content("How much do you earn?")
          .shouldBeAsked(false)
          .build());
        session.flush();

        char codeTestedQuestionShouldBeAskedDbValue = session.createNativeQuery(format("SELECT shouldBeAsked FROM Question WHERE id='%s'", codeTestedQuestionId), Character.class)
          .getSingleResult();
        char earningsQuestionsShouldBeAskedDbValue = session.createNativeQuery(format("SELECT shouldBeAsked FROM Question WHERE id='%s'", earningsQuestionId), Character.class)
          .getSingleResult();
        session.close();

        assertEquals('T', codeTestedQuestionShouldBeAskedDbValue);
        assertEquals('F', earningsQuestionsShouldBeAskedDbValue);
    }

    @Test
    void whenFieldAnnotatedWithNumericBooleanConverter_ThenConversionWorks() {
        session.beginTransaction();
        UUID earthFlatQuestionId = UUID.randomUUID();
        UUID shouldLearnProgrammingQuestionId = UUID.randomUUID();
        session.persist(new QuestionBuilder().id(earthFlatQuestionId)
          .content("Is the Earth flat?")
          .isEasy(true)
          .build());
        session.persist(new QuestionBuilder().id(shouldLearnProgrammingQuestionId)
          .content("Should one learn programming")
          .isEasy(false)
          .build());
        session.flush();

        int earthFlatQuestionIsEasyDbValue = session.createNativeQuery(format("SELECT isEasy FROM Question WHERE id='%s'", earthFlatQuestionId), Integer.class)
          .getSingleResult();
        int shouldLearnProgrammingQuestionIsEasyDbValue = session.createNativeQuery(format("SELECT isEasy FROM Question WHERE id='%s'", shouldLearnProgrammingQuestionId), Integer.class)
          .getSingleResult();
        session.close();

        assertEquals(1, earthFlatQuestionIsEasyDbValue);
        assertEquals(0, shouldLearnProgrammingQuestionIsEasyDbValue);
    }

    @Test
    void givenFieldAnnotatedWithYesNoConverter_WhenDbValueIsLowercase_ThenDomainModelValueNull() {
        session.beginTransaction();
        UUID mappedToNullQuestionId = UUID.randomUUID();
        UUID behaviorIntuitiveQuestionId = UUID.randomUUID();
        session.createNativeMutationQuery(format("INSERT INTO Question (id, content, correctAnswer) VALUES ('%s', 'Will correctAnswer be mapped to null?', 'y')", mappedToNullQuestionId))
          .executeUpdate();
        session.createNativeMutationQuery(format("INSERT INTO Question (id, content, correctAnswer) VALUES ('%s', 'Is this behavior intuitive?', 'n')", behaviorIntuitiveQuestionId))
          .executeUpdate();

        Question behaviorIntuitiveQuestion = session.get(Question.class, behaviorIntuitiveQuestionId);
        Question mappedToNullQuestion = session.get(Question.class, mappedToNullQuestionId);
        session.close();

        assertNull(behaviorIntuitiveQuestion.getCorrectAnswer());
        assertNull(mappedToNullQuestion.getCorrectAnswer());
    }

    @Test
    void givenConverterRegisteredToAutoApply_whenFieldIsNotAnnotated_ThenConversionWorks() {
        session.beginTransaction();
        UUID likeJavaQuestionId = UUID.randomUUID();
        UUID likeKotlinQuestionId = UUID.randomUUID();
        session.persist(new QuestionBuilder().id(likeJavaQuestionId)
          .content("Do you like Java?")
          .wasAskedBefore(true)
          .build());
        session.persist(new QuestionBuilder().id(likeKotlinQuestionId)
          .content("Do you like Kotlin?")
          .wasAskedBefore(false)
          .build());
        session.flush();

        char likeJavaQuestionWasAskedBeforeDbValue = session.createNativeQuery(format("SELECT wasAskedBefore FROM Question WHERE id='%s'", likeJavaQuestionId), Character.class)
          .getSingleResult();
        char likeKotlinQuestionWasAskedBeforeDbValue = session.createNativeQuery(format("SELECT wasAskedBefore FROM Question WHERE id='%s'", likeKotlinQuestionId), Character.class)
          .getSingleResult();
        session.close();

        assertEquals('Y', likeJavaQuestionWasAskedBeforeDbValue);
        assertEquals('N', likeKotlinQuestionWasAskedBeforeDbValue);
    }
}
