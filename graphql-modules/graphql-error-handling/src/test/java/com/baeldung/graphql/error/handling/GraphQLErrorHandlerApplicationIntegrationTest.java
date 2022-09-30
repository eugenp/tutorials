package com.baeldung.graphql.error.handling;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.baeldung.graphql.error.handling.TestUtils.readFile;
import static java.lang.String.format;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = GraphQLErrorHandlerApplication.class)
public class GraphQLErrorHandlerApplicationIntegrationTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private static final String GRAPHQL_TEST_REQUEST_PATH = "graphql/request/%s.graphql";
    private static final String GRAPHQL_TEST_RESPONSE_PATH = "graphql/response/%s.json";

    @Test
    public void whenUnknownOperation_thenRespondWithRequestError() throws IOException, JSONException {
        String graphqlName = "request_error_unknown_operation";
        GraphQLResponse actualResponse = graphQLTestTemplate.postForResource(format(GRAPHQL_TEST_REQUEST_PATH, graphqlName));
        String expectedResponse = readFile(format(GRAPHQL_TEST_RESPONSE_PATH, graphqlName));

        JSONAssert.assertEquals(expectedResponse, actualResponse.getRawResponse().getBody(), true);
    }

    @Test
    public void whenInvalidSyntaxRequest_thenRespondWithRequestError() throws IOException, JSONException {
        String graphqlName = "request_error_invalid_request_syntax";
        GraphQLResponse actualResponse = graphQLTestTemplate.postForResource(format(GRAPHQL_TEST_REQUEST_PATH, graphqlName));
        String expectedResponse = readFile(format(GRAPHQL_TEST_RESPONSE_PATH, graphqlName));

        JSONAssert.assertEquals(expectedResponse, actualResponse.getRawResponse().getBody(), true);
    }

    @Test
    public void whenRequestAllNonNullField_thenRespondPartialDataWithFieldError() throws IOException, JSONException {
        String graphqlName = "field_error_request_non_null_fields_partial_response";
        GraphQLResponse actualResponse = graphQLTestTemplate.postForResource(format(GRAPHQL_TEST_REQUEST_PATH, graphqlName));
        String expectedResponse = readFile(format(GRAPHQL_TEST_RESPONSE_PATH, graphqlName));

        JSONAssert.assertEquals(expectedResponse, actualResponse.getRawResponse().getBody(), true);
    }
}
