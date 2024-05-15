package com.baeldung.springbootinitialload.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.Application;
import com.baeldung.boot.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Sql(scripts = {"/employees_schema.sql", "/import_employees.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = {"/delete_employees_data.sql"}, executionPhase = AFTER_TEST_CLASS)
public class SpringBootInitialLoadIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testLoadDataForTestClass() {
        assertEquals(3, employeeRepository.findAll().size());
    }

    @Test
    @Sql(scripts = {"/import_senior_employees.sql"}, executionPhase = BEFORE_TEST_METHOD ,config = @SqlConfig(encoding = "utf-8", transactionMode = TransactionMode.ISOLATED))
    public void testLoadDataForTestCase() {
        assertEquals(5, employeeRepository.findAll().size());
    }
}
