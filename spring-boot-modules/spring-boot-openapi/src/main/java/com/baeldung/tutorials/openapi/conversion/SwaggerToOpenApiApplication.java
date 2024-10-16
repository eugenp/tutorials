package com.baeldung.tutorials.openapi.conversion;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class SwaggerToOpenApiApplication {

    private static final String FILE_OPEN_API_2_SPECIFICATION = "/OpenAPI2/swagger.json";

    public static void main(String[] args) throws IOException {
        OpenAPI openAPI = processSpecificationFile();
        printSpecification(openAPI);
    }

    private static OpenAPI processSpecificationFile() {
        SwaggerParseResult result = new OpenAPIParser().readLocation(FILE_OPEN_API_2_SPECIFICATION, null, null);

        return result.getOpenAPI();
    }

    private static void printSpecification(OpenAPI openAPI) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String jsonString = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(openAPI);

        System.out.println(jsonString);
    }

}
