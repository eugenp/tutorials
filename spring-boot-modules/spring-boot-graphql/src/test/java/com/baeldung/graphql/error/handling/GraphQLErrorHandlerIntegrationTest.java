package com.baeldung.graphql.error.handling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static graphql.ErrorType.NullValueInNonNullableField;
import static org.springframework.graphql.execution.ErrorType.INTERNAL_ERROR;
import static org.springframework.graphql.execution.ErrorType.NOT_FOUND;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GraphQLErrorHandlerApplication.class)
@ActiveProfiles("error-handling")
public class GraphQLErrorHandlerIntegrationTest {

    private static final String GRAPHQL_TEST_REQUEST_PATH = "src/test/resources/graphql-files/request/%s_request.graphql";
    private static final String GRAPHQL_TEST_RESPONSE_PATH = "src/test/resources/graphql-files/response/%s_response.json";

    @Autowired
    private HttpGraphQlTester graphQlTester;

    @Test
    void whenMandatoryFieldNull_thenRespondWithResponseError() throws IOException {
        String nonNullFieldScenario = "non_null_field";

        graphQlTester.document(fileToRequest(nonNullFieldScenario))
          .execute()
          .errors()
          .expect(error -> error.getErrorType() == NullValueInNonNullableField)
          .verify()
          .path("$.data")
          .matchesJson(fileToResponse(nonNullFieldScenario));
    }

    @Test
    void whenUnhandledException_thenRespondWithGenericError() throws IOException {
        String unhandledExceptionScenario = "unhandled_exception";

        graphQlTester.document(fileToRequest(unhandledExceptionScenario))
          .execute()
          .errors()
          .expect(error -> error.getErrorType() == INTERNAL_ERROR)
          .verify()
          .path("$.data")
          .valueIsNull();
    }

    @Test
    void whenHandledException_thenRespondWithCustomErrorDetails() throws IOException {
        String handledExceptionScenario = "handled_exception";

        graphQlTester.document(fileToRequest(handledExceptionScenario))
          .execute()
          .errors()
          .expect(error -> error.getErrorType() == NOT_FOUND
            && "Vehicle with vin: 123 not found.".equals(error.getMessage()))
          .verify()
          .path("$.data")
          .matchesJson("{\n"
            + "        \"searchByVin\": null\n"
            + "    }");
    }

    @Test
    void whenNoException_thenRespondWithNoError() throws IOException {
        String noExceptionScenario = "no_exception";

        graphQlTester.document(fileToRequest(noExceptionScenario))
          .execute()
          .path("$.data")
          .matchesJson(fileToResponse(noExceptionScenario));
    }

    private static String fileToRequest(String fileName) throws IOException {
        Path path = Paths.get(String.format(GRAPHQL_TEST_REQUEST_PATH, fileName));
        return new String(Files.readAllBytes(path));
    }

    private static String fileToResponse(String fileName) throws IOException {
        Path path = Paths.get(String.format(GRAPHQL_TEST_RESPONSE_PATH, fileName));
        return new String(Files.readAllBytes(path));
    }
}
