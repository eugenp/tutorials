package com.baeldung.h2execscript;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = H2ExecScriptDemoApplication.class)
@ActiveProfiles("h2-exec-sql")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class H2ExecSqlIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void whenUsingRunscriptInJdbcUrl_thenSqlExecuted() {
        List<String> expectedTaskNames = List.of("Start the application", "Check if data table is filled");
        List<String> taskNames = entityManager.createNativeQuery("SELECT NAME FROM TASK_TABLE ORDER BY ID")
            .getResultStream()
            .map(Object::toString)
            .toList();
        assertEquals(expectedTaskNames, taskNames);
    }

    @Test
    @Order(1)
    void whenSpringAutoDetectSchemaAndDataSql_thenSqlExecuted() {
        List<String> expectedCityNames = List.of("New York", "Hamburg", "Shanghai");
        List<String> cityNames = entityManager.createNativeQuery("SELECT NAME FROM CITY ORDER BY ID")
            .getResultStream()
            .map(Object::toString)
            .toList();
        assertEquals(expectedCityNames, cityNames);
    }

    @Test
    @Order(2)
    void whenRunscriptInNativeQuery_thenSqlExecuted() {
        entityManager.createNativeQuery("RUNSCRIPT FROM 'classpath:/sql/add_cities.sql'")
            .executeUpdate();
        List<String> expectedCityNames = List.of("New York", "Hamburg", "Shanghai", "Paris", "Berlin", "Tokyo");
        List<String> cityNames = entityManager.createNativeQuery("SELECT NAME FROM CITY ORDER BY ID")
            .getResultStream()
            .map(Object::toString)
            .toList();
        assertEquals(expectedCityNames, cityNames);
    }

}