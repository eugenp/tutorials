package com.baeldung.h2seq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

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
@ActiveProfiles("h2-seq-oracle")
@Transactional
public class H2SeqAsOracleDemoIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void whenCreateH2SequenceWithDefaultOptions_thenGetExpectedNextValueFromSequence() {
        entityManager.createNativeQuery("CREATE SEQUENCE my_seq")
            .executeUpdate();

        String sqlNextValueFor = "SELECT NEXT VALUE FOR my_seq";
        BigDecimal nextValueH2 = (BigDecimal) entityManager.createNativeQuery(sqlNextValueFor)
            .getSingleResult();
        assertEquals(0, BigDecimal.ONE.compareTo(nextValueH2));

        String sqlNextValueOralceStyle = "SELECT my_seq.nextval FROM dual";
        BigDecimal nextValueOracle = (BigDecimal) entityManager.createNativeQuery(sqlNextValueOralceStyle)
            .getSingleResult();
        assertEquals(0, BigDecimal.TWO.compareTo(nextValueOracle));

        String sqlNextValueFunction = "SELECT nextval('my_seq')";
        nextValueOracle = (BigDecimal) entityManager.createNativeQuery(sqlNextValueFunction)
            .getSingleResult();
        assertEquals(0, BigDecimal.valueOf(3)
            .compareTo(nextValueOracle));

        entityManager.createNativeQuery("DROP SEQUENCE my_seq")
            .executeUpdate();
    }
}