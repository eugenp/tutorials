package com.baeldung.testcontainers.jdbc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FullTestcontainersLifecycleLiveTest {

	@Autowired
	HobbitRepository theShire;

	static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine").withDatabaseName("test-db");

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Test
	void whenCallingSave_thenEntityIsPersistedToDb() {
		theShire.save(new Hobbit("Bilbo Baggins"));

		assertThat(theShire.findAll())
		  .hasSize(1).first()
		  .extracting(Hobbit::getName)
		  .isEqualTo("Bilbo Baggins");
	}
}
