package com.baeldung.swaggerparser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class SwaggerParserUnitTest {

    private static String OPENAPI_SPECIFICATION_STRING = "{\"openapi\":\"3.0.0\",\"info\":{\"title\":\"User APIs\",\"version\":\"1.0\"},\"servers\":[{\"url\":\"https://jsonplaceholder.typicode.com\",\"description\":\"Json Place Holder Service\"}],\"paths\":{\"/users/{id}\":{\"parameters\":[{\"schema\":{\"type\":\"integer\"},\"name\":\"id\",\"in\":\"path\",\"required\":true}],\"get\":{\"summary\":\"Fetch user by ID\",\"tags\":[\"User\"],\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\"},\"name\":{\"type\":\"string\"}}}}}}},\"operationId\":\"get-users-user_id\",\"description\":\"Retrieve a specific user by ID\"}}}}";

    @Test
    public void givenAValidOpenAPIYAMLFile_whenParsing_thenCheckIfParsed() {
        SwaggerParseResult result = new OpenAPIParser().readLocation("valid-openapi-sample.yml", null, null);

        OpenAPI openAPI = result.getOpenAPI();

        assertNotNull(openAPI);
    }

    @Test
    public void givenAValidOpenAPIYAMLFile_whenParsing_thenCheckIfNotErrors() {
        SwaggerParseResult result = new OpenAPIParser().readLocation("valid-openapi-sample.yml", null, null);

        List<String> messages = result.getMessages();

        assertTrue(messages.isEmpty());
    }

    @Test
    public void givenAnInvalidOpenAPIYAMLFile_whenParsing_thenCheckIfErrors() {
        SwaggerParseResult result = new OpenAPIParser().readLocation("invalid-openapi-sample.yml", null, null);

        List<String> messages = result.getMessages();

        assertFalse(messages.isEmpty());
    }

    @Test
    public void givenAValidOpenAPISpecificationString_whenParsing_thenCheckIfParsed() {
        SwaggerParseResult result = new OpenAPIParser().readContents(OPENAPI_SPECIFICATION_STRING, null, null);

        OpenAPI openAPI = result.getOpenAPI();

        assertNotNull(openAPI);
    }

    @Test
    public void givenAValidOpenAPISpecificationString_whenParsing_thenCheckIfNotErrors() {
        SwaggerParseResult result = new OpenAPIParser().readLocation(OPENAPI_SPECIFICATION_STRING, null, null);

        List<String> messages = result.getMessages();

        assertNull(messages);
    }

}
