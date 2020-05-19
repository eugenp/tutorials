package com.baeldung.springbootinitialload.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.Application;
import com.baeldung.boot.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@SqlGroup({ @Sql(scripts = "/employees_schema.sql", config = @SqlConfig(transactionMode = TransactionMode.ISOLATED)),
            @Sql("/import_employees.sql")})
public class SpringBootSqlGroupAnnotationIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testLoadDataForTestCase() {
        assertEquals(3, employeeRepository.findAll().size());
    }
}
