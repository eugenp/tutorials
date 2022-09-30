package com.baeldung.spring_project;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = { "spring.r2dbc.url=r2dbc:tc:mysql:///baeldung?TC_IMAGE_TAG=5.7.34"}
)
@TestInstance(value = PER_CLASS)
@Testcontainers
@Disabled
class StartupIT {

	@Test
	void contextLoads() {
	}

}
