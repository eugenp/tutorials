package com.baeldung.jsonunit;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import java.util.List;

import org.junit.jupiter.api.Test;

class JsonUnitAssertionsUnitTest {

    @Test
    void whenWeVerifyAJsonObject_thenItContainsKeyValueEntries() {
        String articleJson = """ 
                {
                   "name": "A Guide to Spring Boot",
                   "tags": ["java", "spring boot", "backend"]
                }
            """;

        assertThatJson(articleJson).isObject()
            .containsEntry("name", "A Guide to Spring Boot")
            .containsEntry("tags", List.of("java", "spring boot", "backend"));
    }

    @Test
    void whenWeVerifyAJsonArrayField_thenItContainsExpectedValues() {
        String articleJson = """ 
            		{
            		   "name": "A Guide to Spring Boot",
            		   "tags": ["java", "spring boot", "backend"]
            		}
            """;

        assertThatJson(articleJson).isObject()
            .containsEntry("name", "A Guide to Spring Boot")
            .node("tags")
            .isArray()
            .containsExactlyInAnyOrder("java", "spring boot", "backend");
    }

}
