package com.baeldung.tutorials.openapi.conversion;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.tutorials.openapi.conversion.converter.SwaggerToOpenApiConverter;

public class SwaggerToOpenApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerToOpenApiApplication.class);

    private static final String FILE_OPEN_API_2_SPECIFICATION = "/openApi2/swagger.json";

    public static void main(String[] args) throws IOException {
        String converted = SwaggerToOpenApiConverter.convert(FILE_OPEN_API_2_SPECIFICATION);

        logger.info("OpenAPI 3 Specification:\n {}", converted);
    }

}
