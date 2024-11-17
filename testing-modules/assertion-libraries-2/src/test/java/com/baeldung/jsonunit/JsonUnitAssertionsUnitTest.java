package com.baeldung.jsonunit;

import org.junit.jupiter.api.Test;

import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class JsonUnitAssertionsUnitTest {

	@Test
	void test() {
		String articleJson = """ 
						{
						   "id": 100,
						   "name": "A Guide to Spring Boot",
						   "tags": ["java", "spring boot", "backend"],
						   "author": {
						       "id": "john-doe",
						       "name": "John Doe"
						   }
						}
				""";

		assertThatJson(articleJson)
				.isObject()
				.containsEntry("id", 100L)
				.containsEntry("name", "A Guide to Spring Boot")
				.containsEntry("tags", List.of("java", "spring boot", "backend"))
					.node("author").isObject()
					.containsEntry("name", "John Doe");
	}

}
