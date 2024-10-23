package com.baeldung.tutorials.openapi.conversion.converter;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class SwaggerToOpenApiConverter {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private SwaggerToOpenApiConverter() {
        super();
    }

    public static String convert(final String specificationFileLocation) throws IOException {
        if (specificationFileLocation == null || specificationFileLocation.isEmpty()) {
            throw new IllegalArgumentException("Specification file path cannot be null or empty.");
        }

        return asJsonString(processSpecificationJson(specificationFileLocation));
    }

    private static OpenAPI processSpecificationJson(final String specificationFileLocation) {
        SwaggerParseResult result = new OpenAPIParser().readLocation(specificationFileLocation, null, null);

        final OpenAPI openAPI = result.getOpenAPI();
        if (openAPI == null) {
            throw new IllegalArgumentException("Failed to parse OpenAPI specification from: " + specificationFileLocation);
        }

        return openAPI;
    }

    private static String asJsonString(final OpenAPI openAPI) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(openAPI);
    }

}
