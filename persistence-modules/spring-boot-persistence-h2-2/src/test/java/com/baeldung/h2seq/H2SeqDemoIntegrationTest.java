package com.baeldung.h2seq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = H2SeqDemoApplication.class)
@ActiveProfiles("h2-seq")
@Transactional
public class H2SeqDemoIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    private final String sqlNextValueFor = "SELECT NEXT VALUE FOR my_seq";
    private final String sqlNextValueFunction = "SELECT nextval('my_seq')";

    @Test
    void whenCreateH2SequenceWithDefaultOptions_thenGetExpectedNextValueFromSequence() {
        entityManager.createNativeQuery("CREATE SEQUENCE my_seq")
            .executeUpdate();

        Long nextValue = (Long) entityManager.createNativeQuery(sqlNextValueFunction)
            .getSingleResult();
        assertEquals(1, nextValue);

        nextValue = (Long) entityManager.createNativeQuery(sqlNextValueFor)
            .getSingleResult();
        assertEquals(2, nextValue);

        nextValue = (Long) entityManager.createNativeQuery(sqlNextValueFunction)
            .getSingleResult();
        assertEquals(3, nextValue);

        entityManager.createNativeQuery("DROP SEQUENCE my_seq")
            .executeUpdate();
    }

    @Test
    void whenCustomizeH2Sequence_thenGetExpectedNextValueFromSequence() {
        entityManager.createNativeQuery("CREATE SEQUENCE my_seq START WITH 1000 INCREMENT BY 10")
            .executeUpdate();

        Long nextValue = (Long) entityManager.createNativeQuery(sqlNextValueFor)
            .getSingleResult();
        assertEquals(1000, nextValue);

        nextValue = (Long) entityManager.createNativeQuery(sqlNextValueFunction)
            .getSingleResult();
        assertEquals(1010, nextValue);

        nextValue = (Long) entityManager.createNativeQuery(sqlNextValueFor)
            .getSingleResult();
        assertEquals(1020, nextValue);

        entityManager.createNativeQuery("DROP SEQUENCE my_seq")
            .executeUpdate();
    }

    @Test
    void whenSaveEntityUsingSequence_thenCorrect() {
        entityManager.createNativeQuery("CREATE SEQUENCE book_seq")
            .executeUpdate();
        Book book1 = new Book("book1");
        assertNull(book1.getId());
        entityManager.persist(book1);
        assertEquals(1, book1.getId());

        Book book2 = new Book("book2");
        entityManager.persist(book2);
        assertEquals(2, book2.getId());
    }
}