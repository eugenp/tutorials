package com.baeldung.tutorials.openapi.conversion.converter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class SwaggerToOpenApiConverterTest {

    private static final String FILE_OPEN_API_2_SPECIFICATION = "/openApi2/swagger.json";

    @Test
    void givenOpenApi2_whenConvert_thenSpecificationSuccessfullyConverted() throws IOException {
        String openAPI3 = SwaggerToOpenApiConverter.convert(FILE_OPEN_API_2_SPECIFICATION);

        assertNotNull(openAPI3);
    }

}