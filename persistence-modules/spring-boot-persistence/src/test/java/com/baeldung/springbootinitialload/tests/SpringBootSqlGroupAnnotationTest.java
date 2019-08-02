package com.baeldung.springbootinitialload.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.Application;
import com.baeldung.boot.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@SqlGroup({
		@Sql(scripts = "/employees_schema_with_comments.sql", config = @SqlConfig(encoding = "utf-8", commentPrefix = "\\", transactionMode = TransactionMode.ISOLATED)),
		@Sql("/import_employees.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = { "/drop_employees.sql" }) })
public class SpringBootSqlGroupAnnotationTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void testLoadDataForTestCase() {
		assertEquals(employeeRepository.findAll().size(), 3);
	}
}
