package com.baeldung.jsonunit;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.javacrumbs.jsonunit.core.Option;

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
    void whenWeVerifyAJsonObject_thenItMatchesAnExpectedJsonString() {
        String articleJson = """ 
                {
                   "name": "A Guide to Spring Boot",
                   "tags": ["java", "spring boot", "backend"]
                }
            """;

        assertThatJson(articleJson).isObject()
            .isEqualTo(json("""
                {
                   "name": "A Guide to Spring Boot",
                   "tags": ["java", "spring boot", "backend"]
                }
                """));
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

    @Test
    void whenWeVerifyTheListOfTags_thenItContainsExpectedStrings() {
        String articleJson = """ 
            		{
            		   "name": "A Guide to Spring Boot",
            		   "tags": ["java", "spring boot", "backend"]
            		}
            """;

        assertThatJson(articleJson).when(Option.IGNORING_ARRAY_ORDER)
            .when(Option.IGNORING_EXTRA_ARRAY_ITEMS)
            .node("tags")
            .isEqualTo(json("""
                    ["backend", "java"]
                """));
    }

    @Test
    void whenWeVerifyJsonObject_thenItMatchesAgainstPlaceholders() {
        String articleJson = """ 
            		{
            		   "name": "A Guide to Spring Boot",
            		   "tags": ["java", "spring boot", "backend"]
            		}
            """;

        assertThatJson(articleJson).isEqualTo(json(""" 
                    {
                       "name": "${json-unit.any-string}",
                       "tags": "${json-unit.ignore-element}"
                    }
            """));
    }

    @Test
    void whenWeVerifyJsonObjectIgnoringTags_thenItEqualsTheExpectedJson() {
        String articleJson = """ 
            		{
            		   "name": "A Guide to Spring Boot",
            		   "tags": ["java", "spring boot", "backend"]
            		}
            """;

        assertThatJson(articleJson).whenIgnoringPaths("tags")
            .isEqualTo(json(""" 
                        {
                           "name": "A Guide to Spring Boot",
                           "tags": [ "ignored", "tags" ]
                        }
                """));
    }
}
